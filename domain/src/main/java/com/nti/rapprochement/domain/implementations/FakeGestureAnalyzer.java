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
        HandLandmarkerResult result;

        try {
            // пытаемся распознать точки
            MPImage mpImage = new BitmapImageBuilder(bitmap).build();
            result = handLandmarker.detect(mpImage);
        } catch (MediaPipeException e) {
            // в случае неудачи ничего не делаем, выходим
            return;
        }

        /*
         * Тут же вызываю колбеки (для простоты). Допустимо, если анализ кадров
         * будет распределен по многим потокам. Колбеки можно вызывать из любого потока.
         */
        if (previewChangeCallback != null) {
            // рисуем схему точек
            Bitmap analyzePreview = drawPreview(result, bitmap.getWidth(), bitmap.getHeight());
            // вызываем колбек для передачи схемы в интерфейс
            previewChangeCallback.accept(analyzePreview);
        }

        if (textChangeCallback != null) {
            // вызываем колбек, передаем фейковый текст
            textChangeCallback.accept(getDetectedText());
        }

        // нужно для генерации фейкового текста
        frameCounter++;
    }

    @Override
    public void dispose() {
        // закрываю лендмаркер, тут же можно закрывать открытые потоки и др. требующие этого объекты
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
        int wordsCount = frameCounter / TEXT_REFRESH_RATE_EACH_N_FRAME;
        return trimAnyWordsFromStart(FAKE_TEXT, wordsCount);
    }

    private static String trimAnyWordsFromStart(String text, int any) {
        String[] words = text.split(" ");

        if (any > words.length) {
            return text;
        }

        StringBuilder trimmedText = new StringBuilder();
        for (int i = 0; i < any; i++) {
            trimmedText.append(words[i]);
            if (i < any - 1) {
                trimmedText.append(" ");
            }
        }

        return trimmedText.toString();
    }
}
