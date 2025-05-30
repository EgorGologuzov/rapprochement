package com.nti.rapprochement.views;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.function.Consumer;

public class ModeInputGesture extends RecordCallVM.Mode {

    private IGestureAnalyzer analyzer;
    private HelperInputTimer inputTimer;
    private HelperLiveText liveText;
    private HelperGesturePreview gesturePreview;

    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.mode_input_gesture, parent);
        RoundPreview preview = view.findViewById(R.id.preview);
        RoundPreview skeletonPreview = view.findViewById(R.id.skeletonPreview);
        TextView timeView = view.findViewById(R.id.timeView);
        FrameLayout timeLine = view.findViewById(R.id.timeLine);
        TextView outputView = view.findViewById(R.id.outputView);
        ProgressBar cameraLoadingSpinner = view.findViewById(R.id.cameraLoadingSpinner);

        analyzer = Domain.getGestureAnalyzer(parent.getContext());

        Runnable startTimer = () -> {
            HelperInputTimer.CreateArgs ca = new HelperInputTimer.CreateArgs();
            ca.timeView = timeView;
            ca.timeLine = timeLine;
            ca.timeLineParent = parent;
            ca.timeoutSec = Settings.getGestureRecognizeTimeout();
            ca.onTimeout = vm::finishInputOrShow;
            inputTimer = new HelperInputTimer(ca);
        };

        Runnable createLiveText = () -> {
            HelperLiveText.CreateArgs ca = new HelperLiveText.CreateArgs();
            ca.target = outputView;
            ca.vm = vm;
            ca.lightedTextBackground = Res.color(R.color.primary_1);
            ca.lightedTextForeground = Color.BLACK;
            liveText = new HelperLiveText(ca);
        };

        Runnable startPreview = () -> {
            HelperGesturePreview.CreateArgs ca = new HelperGesturePreview.CreateArgs();
            ca.preview = preview;
            ca.skeletonPreview = skeletonPreview;
            ca.analyzer = analyzer;
            gesturePreview = new HelperGesturePreview(ca);
        };

        Runnable showCameraProblemsMessage = () -> {
            DialogMessage.show(
                    Res.str(R.string.message_camera_permission_problems),
                    vm::removeSelfFromHistory
            );
        };

        Runnable run = () -> {
            createLiveText.run();
            startPreview.run();
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

        Consumer<RoundPreview.ImageChangeEventArgs> handlePreviewStreamingStart = e -> {
            cameraLoadingSpinner.setVisibility(View.GONE);
            startTimer.run();
            preview.setOnImageChangeListener(null);
        };

        Consumer<String> handleRecognizedTextChanged = text -> {
            App.current.runOnUiThread(() -> liveText.setText(text));
        };

        vm.setPermissionEventListener(handlePermissionRequestResult);
        preview.setOnImageChangeListener(handlePreviewStreamingStart);
        analyzer.setTextChangeCallback(handleRecognizedTextChanged);

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
                    vm.removeSelfFromHistory();
                });

        view.findViewById(R.id.toggleCameraButton)
                .setOnClickListener(v -> {
                    Camera.toggleFacingForAllSessions();
                });

        view.findViewById(R.id.toTextButton)
                .setOnClickListener(v -> {
                    if (checkTextNotEmpty(vm)) {
                        vm.activateMode(new ModeShowText());
                    }
                });

        view.findViewById(R.id.toSoundButton)
                .setOnClickListener(v -> {
                    if (checkTextNotEmpty(vm)) {
                        vm.activateMode(new ModeShowSound());
                    }
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

        if (analyzer != null) {
            analyzer.dispose();
        }

        if (inputTimer != null) {
            inputTimer.dispose();
        }

        if (gesturePreview != null) {
            gesturePreview.dispose();
        }
    }

    private boolean checkTextNotEmpty(RecordCallVM vm) {
        if (TextUtils.isEmpty(vm.getText())) {
            App.current.showToast(R.string.toast_text_not_recognized);
            return false;
        }
        return true;
    }
}
