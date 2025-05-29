package com.nti.rapprochement.views;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nti.rapprochement.App;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HelperInputTimer {

    private static final int DELAY = 0;
    private static final int PERIOD = 50;

    public static class CreateArgs {
        TextView timeView;
        FrameLayout timeLine;
        ViewGroup timeLineParent;
        float timeoutSec;
        Runnable onTimeout;
    }

    private final Timer timer;

    public HelperInputTimer(CreateArgs args) {
        TextView timeView = args.timeView;
        FrameLayout timeLine = args.timeLine;
        ViewGroup timeLineParent = args.timeLineParent;
        float timeout = args.timeoutSec;
        Runnable onTimeout = args.onTimeout;

        timer = new Timer();

        timer.schedule(new TimerTask() {
            final Date startTime = new Date();
            @Override
            public void run() {
                App.current.runOnUiThread(() -> {
                    float timeDelta = (float) (new Date().getTime() - startTime.getTime()) / 1000;
                    float timeRemain = timeout - timeDelta;

                    if (timeRemain <= 0) {
                        timer.cancel();
                        timer.purge();
                        if (onTimeout != null) {
                            onTimeout.run();
                        }
                        return;
                    }

                    String timeStr = formatTime((int) Math.ceil(timeRemain), (int) timeout);
                    timeView.setText(timeStr);

                    float percent = timeRemain / timeout;
                    int targetWidth = Math.round(percent * timeLineParent.getWidth());
                    ViewGroup.LayoutParams params = timeLine.getLayoutParams();
                    params.width = targetWidth;
                    timeLine.setLayoutParams(params);
                });
            }
        }, DELAY, PERIOD);
    }

    public void dispose() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private static String formatTime(int timeRemain, int timeDefault) {
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
