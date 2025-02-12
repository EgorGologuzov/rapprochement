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

public class ModeInputGesture extends RecordCallVM.Mode {
    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.mode_input_gesture, parent);
        RoundPreview preview = view.findViewById(R.id.preview);
        RoundPreview analyzeResultView = view.findViewById(R.id.analyzeResultView);
        TextView timeView = view.findViewById(R.id.timeView);
        FrameLayout timeLine = view.findViewById(R.id.timeLine);

        if (!Permissions.hasPermissionCamera()) {
            Permissions.requestPermissionCamera();
        }

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

        vm.setPermissionEventListener(result -> {
            if (result.type == Permissions.Type.Camera && result.result == Permissions.Result.Denied) {
                MessageDialog.show(R.string.message_camera_permission_problems, () -> {
                        vm.deactivatePanel();
                        vm.removeSelfFromHistory();
                    }
                );
            }
        });

//        Camera.startAnalyze(imageProxy -> {
//            final Bitmap result = GestureTranslator.analyze(imageProxy.toBitmap());
//            final Bitmap formatedResult = Camera.formatCircle(result);
//            App.runOnUiThread(() -> {
//                analyzeResultView.setRotation(imageProxy.getImageInfo().getRotationDegrees());
//                analyzeResultView.setImageBitmap(formatedResult);
//            });
//        });

//        RModeInputGesture mode = (RModeInputGesture) model.getMode();
//
//        mode.setStartTime(new Date());
//
//        optionalData.timerId = GlobalTimer.addThreadSafetyTimer(() -> {
//            float timeDelta = (float) (new Date().getTime() - mode.getStartTime().getTime()) / 1000;
//            float timeDefault = 10;
//            float timeRemain = timeDefault - timeDelta;
//
//            if (timeRemain <= 0) {
//                model.finishInputOrShow();
//            }
//
//            String timeStr = formatTime((int) timeRemain, (int) timeDefault);
//            timeView.setText(timeStr);
//
//            float percent = timeRemain / timeDefault;
//            int targetWidth = Math.round(percent * parent.getWidth());
//
//            ViewGroup.LayoutParams params = timeLine.getLayoutParams();
//            params.width = targetWidth;
//            timeLine.setLayoutParams(params);
//        }, 0, 10);

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
    }
}
