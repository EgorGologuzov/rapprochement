package com.nti.rapprochement.views;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.data.Camera;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;

public class HelperGesturePreview {

    private static final long FRAME_DEPRECATION_TIMING = 30;

    public static class CreateArgs {
        public RoundPreview preview;
        public RoundPreview skeletonPreview;
        public IGestureAnalyzer analyzer;
    }

    public HelperGesturePreview(CreateArgs args) {
        RoundPreview preview = args.preview;
        RoundPreview analyzePreview = args.skeletonPreview;
        IGestureAnalyzer analyzer = args.analyzer;

        // Чистое превью с камеры

        Camera.openAnalyzeSession(imageInfo -> {
            final float rotation = imageInfo.rotationDegrees;
            final boolean isFacingFront = Settings.getLastCameraFacing() == Settings.CameraFacing.Front;
            final Bitmap bitmap = imageInfo.bitmap;

            App.current.runOnUiThread(() -> {
                preview.setRotation(rotation);
                preview.setMirrorMode(isFacingFront);
                preview.setImageBitmap(bitmap);
            });
        });

        // Превью скелета руки

        analyzer.setPreviewChangeCallback(bitmap -> {
            App.current.runOnUiThread(() -> analyzePreview.setImageBitmap(bitmap));
        });

        Camera.openAnalyzeSession(imageInfo -> {
            if (System.currentTimeMillis() - imageInfo.timestamp > FRAME_DEPRECATION_TIMING) {
                return;
            }

            final float rotation = imageInfo.rotationDegrees;
            final boolean isFacingFront = Settings.getLastCameraFacing() == Settings.CameraFacing.Front;
            final Bitmap bitmap = imageInfo.bitmap;

            analyzer.analyze(bitmap, rotation);

            App.current.runOnUiThread(() -> {
                analyzePreview.setRotation(rotation);
                analyzePreview.setMirrorMode(isFacingFront);
            });
        });
    }

    public void dispose() {
        Camera.closeAllAnalyzeSessions();
    }
}
