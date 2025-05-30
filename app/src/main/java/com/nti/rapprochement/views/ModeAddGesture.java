package com.nti.rapprochement.views;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.nti.rapprochement.domain.contracts.IGestureStorage;
import com.nti.rapprochement.models.RecordCall;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ModeAddGesture extends RecordCallVM.Mode {

    private static final int TIMER_DELAY = 1000;
    private static final int TIMER_PERIOD = 1000;

    private int snapshotTiming;
    private int minSnapshotsCount;
    private int maxSnapshotsCount;

    private int countdown;
    private int snapshotsCounter;

    private IGestureAnalyzer analyzer;
    private IGestureStorage gestureStorage;
    private HelperGesturePreview gesturePreview;
    private Timer timer;

    private ArrayList<Bitmap> snapshots;
    private Bitmap lastSnapshot;
    private float snapshotRotation;

    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.mode_add_gesture, parent);
        RoundPreview preview = view.findViewById(R.id.preview);
        RoundPreview skeletonPreview = view.findViewById(R.id.skeletonPreview);
        ProgressBar cameraLoadingSpinner = view.findViewById(R.id.cameraLoadingSpinner);
        TextView countdownView = view.findViewById(R.id.countdownView);
        TextView frameCounterView = view.findViewById(R.id.frameCounterView);
        FrameLayout flashLayout = view.findViewById(R.id.flashLayout);

        analyzer = Domain.getGestureAnalyzer(parent.getContext());
        gestureStorage = Domain.getGestureStorage(parent.getContext());

        snapshotTiming = Settings.getAddGestureModeSnapshotTiming();
        minSnapshotsCount = Settings.getAddGestureModeSnapshotsMinCount();
        maxSnapshotsCount = Settings.getAddGestureModeSnapshotsMaxCount();
        countdown = snapshotTiming;
        snapshotsCounter = 0;

        snapshots = new ArrayList<>();

        frameCounterView.setText(String.format(Res.str(R.string.pattern_for_snapshots_counter), snapshotsCounter, maxSnapshotsCount));

        Runnable startTimer = () -> {
            timer = new Timer();

            Animation flash = AnimationUtils.loadAnimation(parent.getContext(), R.anim.flash);
            Animation disappearance = AnimationUtils.loadAnimation(parent.getContext(), R.anim.disappearance);
            disappearance.setDuration(TIMER_PERIOD);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    App.current.runOnUiThread(() -> {
                        countdownView.startAnimation(disappearance);

                        if (countdown == 0) {
                            snapshotsCounter++;
                            snapshots.add(lastSnapshot);
                            snapshotRotation = preview.getRotation();

                            if (snapshotsCounter >= maxSnapshotsCount) {
                                saveGesture(vm);
                                return;
                            }

                            countdownView.setText(R.string.message_snapshot_maked);
                            frameCounterView.setText(String.format(Res.str(R.string.pattern_for_snapshots_counter), snapshotsCounter, maxSnapshotsCount));
                            flashLayout.startAnimation(flash);
                            countdown = snapshotTiming;

                            return;
                        }

                        countdownView.setText(String.valueOf(countdown));
                        countdown--;
                    });
                }
            }, TIMER_DELAY, TIMER_PERIOD);
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

        Consumer<RoundPreview.ImageChangeEventArgs> handlePreviewImageChange = new Consumer<RoundPreview.ImageChangeEventArgs>() {
            private boolean isFirstCall = true;
            @Override
            public void accept(RoundPreview.ImageChangeEventArgs e) {
                if (isFirstCall) {
                    cameraLoadingSpinner.setVisibility(View.GONE);
                    startTimer.run();
                    isFirstCall = false;
                }

                lastSnapshot = e.newBitmap;
            }
        };

        vm.setPermissionEventListener(handlePermissionRequestResult);
        preview.setOnImageChangeListener(handlePreviewImageChange);

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

        View view = ViewsUtils.createView(R.layout.panel_add_gesture, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    vm.removeSelfFromHistory();
                });

        view.findViewById(R.id.toggleCameraButton)
                .setOnClickListener(v -> {
                    Camera.toggleFacingForAllSessions();
                });

        view.findViewById(R.id.saveGestureButton)
                .setOnClickListener(v -> {
                    if (snapshotsCounter < minSnapshotsCount) {
                        App.current.showToast(R.string.message_too_few_snapshots_for_save);
                        return;
                    }
                    saveGesture(vm);
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

        if (gesturePreview != null) {
            gesturePreview.dispose();
        }

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void saveGesture(RecordCallVM vm) {
        vm.activateMode(new ModeLoading());

        Consumer<String> handleSuccessNameInput = name -> {
            gestureStorage.saveGesture(name, snapshots, snapshotRotation, status -> {
                App.current.runOnUiThread(() -> {
                    if (status == IGestureStorage.SaveStatus.Success) {
                        vm.setStatus(RecordCall.Status.Success);
                        vm.setText(String.format(Res.str(R.string.pattern_success_saving_message), name));
                    } else {
                        vm.setStatus(RecordCall.Status.Error);
                        vm.setText(String.format(Res.str(R.string.pattern_failed_saving_message), name));
                    }
                    vm.activateMode(new ModeShowText());
                });
            });
        };

        Runnable handleCancelNameInput = () -> {
            vm.removeSelfFromHistory();
        };

        requestGestureName(handleSuccessNameInput, handleCancelNameInput);
    }

    private void requestGestureName(Consumer<String> onOk, Runnable onCancel) {
        Consumer<String> onOkInner = text -> {
            if (TextUtils.isEmpty(text)) {
                requestGestureName(onOk, onCancel);
                return;
            }
            if (onOk != null) {
                onOk.accept(text);
            }
        };

        DialogInputString.show(
                Res.str(R.string.title_gesture_name_dialog),
                onOkInner,
                onCancel
        );
    }
}
