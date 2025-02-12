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

        Runnable startTimer = () -> {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                final Date startTime = new Date();
                @Override
                public void run() {
                    App.current.runOnUiThread(() -> {
                        float timeDelta = (float) (new Date().getTime() - startTime.getTime()) / 1000;
                        float timeDefault = Settings.getGestureRecognizeTimeout();
                        float timeRemain = timeDefault - timeDelta;

                        if (timeRemain <= 0) {
                            vm.finishInputOrShowAndSetTextShowMode();
                            timer.cancel();
                            timer.purge();
                            return;
                        }

                        String timeStr = formatTime((int) timeRemain, (int) timeDefault);
                        timeView.setText(timeStr);

                        float percent = timeRemain / timeDefault;
                        int targetWidth = Math.round(percent * parent.getWidth());
                        ViewGroup.LayoutParams params = timeLine.getLayoutParams();
                        params.width = targetWidth;
                        timeLine.setLayoutParams(params);
                    });
                }
            }, 0, 10);
        };

        if (Permissions.hasPermissionCamera()) {
            startTimer.run();
        } else {
            Permissions.requestPermissionCamera();
        }

        vm.setPermissionEventListener(result -> {
            if (result.type != Permissions.Type.Camera) {
                return;
            }

            if (result.result == Permissions.Result.Denied) {
                MessageDialog.show(
                    R.string.message_camera_permission_problems,
                    () -> {
                        vm.deactivatePanel();
                        vm.removeSelfFromHistory();
                    }
                );
            } else {
                startTimer.run();
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

    private String formatTime(int timeRemain, int timeDefault) {
        int min1 = timeRemain / 60;
        int sec1 = timeRemain % 60;
        int min2 = timeDefault / 60;
        int sec2 = timeDefault % 60;
        String sMin1 = "" + min1;
        String sSec1 = sec1 < 10 ? "0" + sec1 : "" + sec1;
        String sMin2 = "" + min2;
        String sSec2 = sec2 < 10 ? "0" + sec2 : "" + sec2;

        return sMin1 + ":" + sSec1 + " / " + sMin2 + ":" + sSec2;
    }
}
