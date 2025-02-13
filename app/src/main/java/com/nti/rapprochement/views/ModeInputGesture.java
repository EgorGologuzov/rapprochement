package com.nti.rapprochement.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Camera;
import com.nti.rapprochement.data.Permissions;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.domain.Domain;
import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.Timer;
import java.util.function.Consumer;

public class ModeInputGesture extends RecordCallVM.Mode {

    private Timer timer;
    private IGestureAnalyzer analyzer;
    private HelperLiveText liveText;

    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.mode_input_gesture, parent);
        RoundPreview preview = view.findViewById(R.id.preview);
        RoundPreview analyzePreview = view.findViewById(R.id.analyzePreview);
        TextView timeView = view.findViewById(R.id.timeView);
        FrameLayout timeLine = view.findViewById(R.id.timeLine);
        TextView outputTextView = view.findViewById(R.id.outputTextView);

        Runnable startTimer = () -> {
            HelperInputTimer.CreateArgs createArgs = new HelperInputTimer.CreateArgs();
            createArgs.timeView = timeView;
            createArgs.timeLine = timeLine;
            createArgs.timeLineParent = parent;
            createArgs.timeoutSec = Settings.getGestureRecognizeTimeout();
            createArgs.onTimeout = vm::finishInputOrShow;
            timer = HelperInputTimer.create(createArgs);
        };

        Runnable createLiveText = () -> {
            HelperLiveText.CreateArgs createArgs = new HelperLiveText.CreateArgs();
            createArgs.target = outputTextView;
            createArgs.vm = vm;
            createArgs.lightedTextBackground = Res.color(R.color.primary_1);
            createArgs.lightedTextForeground = Color.BLACK;
            liveText = HelperLiveText.create(createArgs);
        };

        Runnable showCameraProblemsMessage = () -> {
            DialogMessage.show(Res.str(R.string.message_camera_permission_problems), () -> {
                    vm.deactivatePanel();
                    vm.removeSelfFromHistory();
                }
            );
        };

        Runnable startPreview = () -> {
            Camera.openAnalyzeSession(imageProxy -> {
                final float rotation = imageProxy.getImageInfo().getRotationDegrees();
                final boolean isFacingFront = Settings.getLastCameraFacing() == Settings.CameraFacing.Front;
                final Bitmap bitmap = imageProxy.toBitmap();

                App.current.runOnUiThread(() -> {
                    preview.setRotation(rotation);
                    preview.setMirrorMode(isFacingFront);
                    preview.setImageBitmap(bitmap);
                });
            });
        };

        Runnable startAnalyze = () -> {
            analyzer = Domain.getGestureAnalyzer(parent.getContext());

            analyzer.setPreviewChangeCallback(bitmap -> {
                App.current.runOnUiThread(() -> analyzePreview.setImageBitmap(bitmap));
            });

            analyzer.setTextChangeCallback(text -> {
                App.current.runOnUiThread(() -> liveText.setText(text));
            });

            Camera.openAnalyzeSession(imageProxy -> {
                final float rotation = imageProxy.getImageInfo().getRotationDegrees();
                final boolean isFacingFront = Settings.getLastCameraFacing() == Settings.CameraFacing.Front;
                final Bitmap bitmap = imageProxy.toBitmap();

                analyzer.analyze(bitmap);

                App.current.runOnUiThread(() -> {
                    analyzePreview.setRotation(rotation);
                    analyzePreview.setMirrorMode(isFacingFront);
                });
            });
        };

        Runnable run = () -> {
            createLiveText.run();
            startPreview.run();
            startAnalyze.run();
            startTimer.run();
        };

        Consumer<Permissions.RequestResult> handlePermissionRequestResult = result -> {
            if (result.type == Permissions.Type.Camera) {
                if (result.result == Permissions.Result.Granted) {
                    run.run();
                } else {
                    showCameraProblemsMessage.run();
                }
            }
        };

        vm.setPermissionEventListener(handlePermissionRequestResult);

        if (Permissions.hasPermissionCamera()) {
            run.run();
        } else {
            Permissions.requestPermissionCamera();
        }

        return view;
    }

    @Override
    public View createPanelView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        View view = ViewsUtils.createView(R.layout.panel_input_gesture, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    vm.deactivatePanel();
                    vm.removeSelfFromHistory();
                });

        view.findViewById(R.id.toggleCameraButton)
                .setOnClickListener(v -> {
                    Camera.toggleFacingForAllSessions();
                });

        view.findViewById(R.id.toTextButton)
                .setOnClickListener(v -> {
                    if (isInputTextEmpty(vm.getText())) return;

                    vm.setMode(new ModeShowText());
                    vm.activatePanel();
                    vm.update();
                });

        view.findViewById(R.id.toSoundButton)
                .setOnClickListener(v -> {
                    if (isInputTextEmpty(vm.getText())) return;

                    vm.setMode(new ModeShowSound());
                    vm.activatePanel();
                    vm.update();
                });

        return view;
    }

    @Override
    public boolean hasPanel() {
        return true;
    }

    @Override
    public void dispose() {
        super.dispose();

        Camera.closeAllAnalyzeSessions();

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        if (analyzer != null) {
            analyzer.dispose();
        }
    }

    private static boolean isInputTextEmpty(String text) {
        if (TextUtils.isEmpty(text)) {
            App.current.showToast(R.string.toast_text_not_recognized);
            return true;
        }

        return false;
    }
}
