package com.nti.rapprochement.data;

import android.graphics.Bitmap;
import android.util.Size;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import androidx.camera.core.resolutionselector.ResolutionStrategy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.nti.rapprochement.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Camera {

    public static class AnalyzeSession {
        public final ExecutorService executor;
        public final Consumer<ImageInfo> analyzer;
        public AnalyzeSession(ExecutorService executor, Consumer<ImageInfo> analyzer) {
            this.executor = executor;
            this.analyzer = analyzer;
        }
    }

    public static class ImageInfo {
        public final float rotationDegrees;
        public final Bitmap bitmap;
        public final long timestamp;
        public ImageInfo(float rotationDegrees, Bitmap bitmap) {
            this.rotationDegrees = rotationDegrees;
            this.bitmap = bitmap;
            this.timestamp = System.currentTimeMillis();
        }
    }

    private static MainActivity mainActivity;
    private static ProcessCameraProvider cameraProvider;
    private final static ArrayList<AnalyzeSession> sessions = new ArrayList<>();
    private static boolean isImageAnalisisBound = false;

    public static void init(MainActivity mainActivity) {
        Camera.mainActivity = mainActivity;

        closeAllAnalyzeSessions();

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(mainActivity);

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, ContextCompat.getMainExecutor(mainActivity));
    }

    public static void openAnalyzeSession(Consumer<ImageInfo> analyzer) {
        AnalyzeSession session = new AnalyzeSession(Executors.newSingleThreadExecutor(), analyzer);
        sessions.add(session);

        if (!isImageAnalisisBound) {
            bindImageAnalysis();
            isImageAnalisisBound = true;
        }
    }

    public static void closeAllAnalyzeSessions() {
        if (cameraProvider == null) {
            return;
        }

        sessions.forEach(s -> s.executor.shutdown());
        sessions.clear();
        cameraProvider.unbindAll();
        isImageAnalisisBound = false;
    }

    public static void toggleFacingForAllSessions() {
        Settings.setLastCameraFacing(
                Settings.getLastCameraFacing() == Settings.CameraFacing.Front
                        ? Settings.CameraFacing.Back
                        : Settings.CameraFacing.Front
        );

        cameraProvider.unbindAll();
        bindImageAnalysis();
    }

    private static void bindImageAnalysis() {
        Size boundSize = new Size(1024, 1024);

        ResolutionSelector resolutionSelector = new ResolutionSelector.Builder()
                .setResolutionStrategy(new ResolutionStrategy(boundSize, ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER))
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        int facing = Settings.getLastCameraFacing() == Settings.CameraFacing.Front
                ? CameraSelector.LENS_FACING_FRONT
                : CameraSelector.LENS_FACING_BACK;

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(facing)
                .build();

        imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(mainActivity),
                imageProxy -> {
                    ImageInfo imageInfo = new ImageInfo(
                            imageProxy.getImageInfo().getRotationDegrees(),
                            imageProxy.toBitmap()
                    );

                    imageProxy.close();

                    sessions.forEach(session -> {
                        session.executor.execute(() -> {
                            if (session.analyzer != null) {
                                session.analyzer.accept(imageInfo);
                            }
                        });
                    });
                }
        );

        cameraProvider.bindToLifecycle(mainActivity, cameraSelector, imageAnalysis);
    }
}
