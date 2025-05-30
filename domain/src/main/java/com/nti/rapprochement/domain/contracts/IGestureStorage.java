package com.nti.rapprochement.domain.contracts;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface IGestureStorage {

    enum SaveStatus {
        Success,
        InvalidName
        // и так далее
    }

    void saveGesture(String gestureName, ArrayList<Bitmap> snapshots, float snapshotsRotation, Consumer<SaveStatus> cb);

    void dispose();
}
