package com.nti.rapprochement.domain.implementations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;

import com.google.mediapipe.framework.MediaPipeException;
import com.google.mediapipe.framework.image.BitmapImageBuilder;
import com.google.mediapipe.framework.image.MPImage;
import com.google.mediapipe.tasks.core.BaseOptions;
import com.google.mediapipe.tasks.vision.core.RunningMode;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult;
import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;

import java.util.function.Consumer;

/**
 * Пример реализации интрефеса IGestureAnalyzer. Используется для тестирования.
 * Использует Mediapipe для распознвания точек рук. Рисует схему на прозрачном Bitmap.
 * Иммитирует распознавание текста: каждые n кадров добавляет к распознанному тексту по одному
 * слову из заранее заданной строки.
 */
public class FakeGestureAnalyzer implements IGestureAnalyzer {

    private static final String HAND_LANDMARKER_MODEL_PATH = "hand_landmarker.task";
    private static final String FAKE_TEXT = "Установлен тестовый анализатор жестов. Настоящий анализатор еще не реализован. В скором будущем мы его реализуем и интегрируем в наше приложение. Конец текста.";
    private static final int TEXT_REFRESH_RATE_EACH_N_FRAME = 3;

    private final HandLandmarker handLandmarker;
    private Consumer<Bitmap> previewChangeCallback;
    private Consumer<String> textChangeCallback;
    private int frameCounter;

    public FakeGestureAnalyzer(Context context) {
        handLandmarker = HandLandmarker.createFromOptions(context, createHandLandmarkerOptions());
        frameCounter = 0;
    }

    @Override
    public void setPreviewChangeCallback(Consumer<Bitmap> callback) {
        // Сохраняем колбек
        this.previewChangeCallback = callback;
    }

    @Override
    public void setTextChangeCallback(Consumer<String> callback) {
        // Сохраняем колбек
        this.textChangeCallback = callback;
    }

    @Override
    public void analyze(Bitmap bitmap) {
        try {
            MPImage mpImage = new BitmapImageBuilder(bitmap).build();
            HandLandmarkerResult result = handLandmarker.detect(mpImage);
            handleHandLandmarkerResult(result, mpImage);
        } catch (MediaPipeException e) {
            return;
        }
    }

    @Override
    public void dispose() {
        handLandmarker.close();
    }

    private HandLandmarker.HandLandmarkerOptions createHandLandmarkerOptions() {
        BaseOptions baseOptions = BaseOptions.builder()
                .setModelAssetPath(HAND_LANDMARKER_MODEL_PATH)
                .build();

        return HandLandmarker.HandLandmarkerOptions.builder()
                .setBaseOptions(baseOptions)
                .setMinHandDetectionConfidence(0.5f)
                .setMinTrackingConfidence(0.5f)
                .setMinHandPresenceConfidence(0.5f)
                .setNumHands(2)
                .setRunningMode(RunningMode.IMAGE)
                .build();
    }

    private void handleHandLandmarkerResult(HandLandmarkerResult result, MPImage input) {
        if (previewChangeCallback != null) {
            Bitmap analyzePreview = drawSkeleton(result, input.getWidth(), input.getHeight());
            previewChangeCallback.accept(analyzePreview);
        }

        if (textChangeCallback != null) {
            textChangeCallback.accept(getFakeText());
        }

        frameCounter++;
    }

    private Bitmap drawSkeleton(HandLandmarkerResult result, int width, int height) {
        final float pointRadius = 12f;
        final float strokeWidth = 8f;
        final float pointsStrokeWidth = 4f;
        final int pointFillColor = Color.parseColor("#A5D8EB");
        final int strokeColor = Color.WHITE;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint pointFillPaint = new Paint();
        pointFillPaint.setColor(pointFillColor);
        pointFillPaint.setStyle(Paint.Style.FILL);
        pointFillPaint.setAntiAlias(true);

        Paint pointStrokePaint = new Paint();
        pointStrokePaint.setColor(strokeColor);
        pointStrokePaint.setStyle(Paint.Style.STROKE);
        pointStrokePaint.setStrokeWidth(pointsStrokeWidth);
        pointStrokePaint.setAntiAlias(true);

        Paint linePaint = new Paint();
        linePaint.setColor(strokeColor);
        linePaint.setStrokeWidth(strokeWidth);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        result.landmarks().forEach(landmark -> {

            HandLandmarker.HAND_CONNECTIONS.forEach(item -> {
                canvas.drawLine(
                        landmark.get(item.start()).x() * width,
                        landmark.get(item.start()).y() * height,
                        landmark.get(item.end()).x() * width,
                        landmark.get(item.end()).y() * height,
                        linePaint
                );
            });

            landmark.forEach(normalizedLandmark -> {
                float x = normalizedLandmark.x() * width;
                float y = normalizedLandmark.y() * height;
                canvas.drawCircle(x, y, pointRadius, pointFillPaint);
                canvas.drawCircle(x, y, pointRadius, pointStrokePaint);
            });
        });

        return bitmap;
    }

    private String getFakeText() {
        int wordsCount = frameCounter / TEXT_REFRESH_RATE_EACH_N_FRAME;
        int spaceCount = 0;
        int targetSpaceIndex = -1;

        for (int i = 0; i < FAKE_TEXT.length(); i++) {
            if (FAKE_TEXT.charAt(i) == ' ') {
                spaceCount++;
            }
            if (spaceCount >= wordsCount) {
                targetSpaceIndex = i;
                break;
            }
        }

        if (targetSpaceIndex == -1) {
            return FAKE_TEXT;
        }

        return FAKE_TEXT.substring(0, targetSpaceIndex);
    }
}