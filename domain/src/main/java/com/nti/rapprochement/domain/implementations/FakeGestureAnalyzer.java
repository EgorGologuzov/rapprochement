package com.nti.rapprochement.domain.implementations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.mediapipe.framework.MediaPipeException;
import com.google.mediapipe.framework.image.BitmapImageBuilder;
import com.google.mediapipe.framework.image.MPImage;
import com.google.mediapipe.tasks.core.BaseOptions;
import com.google.mediapipe.tasks.vision.core.RunningMode;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult;
import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;

import java.util.function.Consumer;

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
        this.previewChangeCallback = callback;
    }

    @Override
    public void setTextChangeCallback(Consumer<String> callback) {
        this.textChangeCallback = callback;
    }

    @Override
    public void analyze(Bitmap bitmap) {
        HandLandmarkerResult result;

        try {
            MPImage mpImage = new BitmapImageBuilder(bitmap).build();
            result = handLandmarker.detect(mpImage);
        } catch (MediaPipeException e) {
            return;
        }

        if (previewChangeCallback != null) {
            Bitmap analyzePreview = drawPreview(result, bitmap.getWidth(), bitmap.getHeight());
            previewChangeCallback.accept(analyzePreview);
        }

        if (textChangeCallback != null) {
            textChangeCallback.accept(getDetectedText());
        }

        frameCounter++;
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

    private Bitmap drawPreview(HandLandmarkerResult result, int width, int height) {
        float scaleFactor = 1f;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.YELLOW);
        pointPaint.setStrokeWidth(8f);
        pointPaint.setStyle(Paint.Style.FILL);

        Paint linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(8f);
        linePaint.setStyle(Paint.Style.STROKE);

        result.landmarks().forEach(landmark -> {
            HandLandmarker.HAND_CONNECTIONS.forEach(item -> {
                canvas.drawLine(
                        landmark.get(item.start()).x() * width * scaleFactor,
                        landmark.get(item.start()).y() * height * scaleFactor,
                        landmark.get(item.end()).x() * width * scaleFactor,
                        landmark.get(item.end()).y() * height * scaleFactor,
                        linePaint
                );
            });

            landmark.forEach(normalizedLandmark -> {
                canvas.drawPoint(
                        normalizedLandmark.x() * width * scaleFactor,
                        normalizedLandmark.y() * height * scaleFactor,
                        pointPaint
                );
            });
        });

        return bitmap;
    }

    private String getDetectedText() {
        int n = frameCounter / TEXT_REFRESH_RATE_EACH_N_FRAME;
        return trimNWordsFromStart(FAKE_TEXT, n);
    }

    public static String trimNWordsFromStart(String text, int n) {
        String[] words = text.split(" ");

        if (n > words.length) {
            return text;
        }

        StringBuilder trimmedText = new StringBuilder();
        for (int i = 0; i < n; i++) {
            trimmedText.append(words[i]);
            if (i < n - 1) {
                trimmedText.append(" ");
            }
        }

        return trimmedText.toString();
    }
}
