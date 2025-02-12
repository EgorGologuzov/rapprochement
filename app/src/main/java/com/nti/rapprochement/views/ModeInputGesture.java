package com.nti.rapprochement.views;

import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ModeInputGesture extends RecordCallVM.Mode {

    private Timer timer;

    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.mode_input_gesture, parent);
        RoundPreview preview = view.findViewById(R.id.preview);
        RoundPreview analyzeResultView = view.findViewById(R.id.analyzeResultView);
        TextView timeView = view.findViewById(R.id.timeView);
        FrameLayout timeLine = view.findViewById(R.id.timeLine);

        Runnable startTimer = () -> {
            InputTimer.StartArgs startArgs = new InputTimer.StartArgs();
            startArgs.timeView = timeView;
            startArgs.timeLine = timeLine;
            startArgs.timeLineParent = parent;
            startArgs.timeoutSec = Settings.getGestureRecognizeTimeout();
            startArgs.onTimeout = vm::finishInputOrShowAndSetTextShowMode;
            timer = InputTimer.start(startArgs);
        };

        Runnable showCameraProblemsMessage = () ->
            MessageDialog.show(R.string.message_camera_permission_problems, () -> {
                    vm.deactivatePanel();
                    vm.removeSelfFromHistory();
                }
            );

        Runnable startPreview = () ->
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

        Runnable run = () -> {
            startPreview.run();
            startTimer.run();
        };

        if (Permissions.hasPermissionCamera()) {
            run.run();
        } else {
            Permissions.requestPermissionCamera();
        }

        vm.setPermissionEventListener(result -> {
            if (result.type == Permissions.Type.Camera) {
                if (result.result == Permissions.Result.Granted) {
                    run.run();
                } else {
                    showCameraProblemsMessage.run();
                }
            }
        });

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
                    vm.setMode(new ModeShowText());
                    vm.activatePanel();
                    vm.update();
                });

        view.findViewById(R.id.toSoundButton)
                .setOnClickListener(v -> {
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
    }
}
