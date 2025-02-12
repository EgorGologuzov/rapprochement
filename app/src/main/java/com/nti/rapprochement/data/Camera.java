package com.nti.rapprochement.data;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Size;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.resolutionselector.ResolutionSelector;
import androidx.camera.core.resolutionselector.ResolutionStrategy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
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
        public final Consumer<ImageProxy> analyzer;
        public AnalyzeSession(ExecutorService executor, Consumer<ImageProxy> analyzer) {
            this.executor = executor;
            this.analyzer = analyzer;
        }
    }

    private static MainActivity mainActivity;
    private static ProcessCameraProvider cameraProvider;
    private final static ArrayList<AnalyzeSession> sessions = new ArrayList<>();

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

    public static void openAnalyzeSession(Consumer<ImageProxy> analyzer) {
        AnalyzeSession session = new AnalyzeSession(Executors.newSingleThreadExecutor(), analyzer);
        sessions.add(session);
        bindAnalyzeSession(session);
    }

    public static void closeAllAnalyzeSessions() {
        if (cameraProvider == null) {
            return;
        }

        cameraProvider.unbindAll();
        sessions.forEach(s -> s.executor.shutdown());
        sessions.clear();
    }

    public static void toggleFacingForAllSessions() {
        Settings.setLastCameraFacing(
                Settings.getLastCameraFacing() == Settings.CameraFacing.Front
                        ? Settings.CameraFacing.Back
                        : Settings.CameraFacing.Front
        );

        cameraProvider.unbindAll();
        sessions.forEach(Camera::bindAnalyzeSession);
    }

    private static ImageAnalysis createImageAnalysis() {
        Size boundSize = new Size(1024, 1024);
        ResolutionSelector resolutionSelector = new ResolutionSelector.Builder()
                .setResolutionStrategy(new ResolutionStrategy(boundSize, ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER))
                .build();

        return new ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
    }

    private static CameraSelector createCameraSelector() {
        int facing = Settings.getLastCameraFacing() == Settings.CameraFacing.Front
                ? CameraSelector.LENS_FACING_FRONT
                : CameraSelector.LENS_FACING_BACK;

        return new CameraSelector.Builder()
                .requireLensFacing(facing)
                .build();
    }

    private static void bindAnalyzeSession(AnalyzeSession session) {
        ImageAnalysis imageAnalysis = createImageAnalysis();
        CameraSelector cameraSelector = createCameraSelector();

        imageAnalysis.setAnalyzer(
                session.executor,
                imageProxy -> {
                    if (session.analyzer != null) {
                        session.analyzer.accept(imageProxy);
                        imageProxy.close();
                    }
                });

        cameraProvider.bindToLifecycle(mainActivity, cameraSelector, imageAnalysis);
    }
}
