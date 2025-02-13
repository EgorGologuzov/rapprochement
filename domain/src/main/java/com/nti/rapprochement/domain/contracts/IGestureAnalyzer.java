package com.nti.rapprochement.domain.contracts;

import android.graphics.Bitmap;

import java.util.function.Consumer;

public interface IGestureAnalyzer {
    void setPreviewChangeCallback(Consumer<Bitmap> callback);
    void setTextChangeCallback(Consumer<String> callback);
    void analyze(Bitmap bitmap);
    void dispose();
}
