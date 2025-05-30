package com.nti.rapprochement.domain.implementations;

import android.content.Context;
import android.graphics.Bitmap;

import com.nti.rapprochement.domain.contracts.IGestureStorage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class FakeGestureStorage implements IGestureStorage {

    private Timer timer;

    public FakeGestureStorage(Context context) {}

    @Override
    public void saveGesture(String gestureName, ArrayList<Bitmap> snapshots, float snapshotsRotation, Consumer<SaveStatus> cb) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gestureName.equals(" ")) {
                    cb.accept(SaveStatus.Success);
                } else {
                    cb.accept(SaveStatus.InvalidName);
                }
            }
        }, 3000);
    }

    @Override
    public void dispose() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
