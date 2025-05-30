package com.nti.rapprochement.domain.implementations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

//import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.framework.MediaPipeException;
import com.google.mediapipe.framework.image.BitmapImageBuilder;
import com.google.mediapipe.framework.image.MPImage;
import com.google.mediapipe.tasks.core.BaseOptions;
import com.google.mediapipe.tasks.vision.core.RunningMode;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult;
import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark;


import java.util.List;
import java.util.function.Consumer;

/**
 * Пример реализации интрефеса IGestureAnalyzer. Используется для тестирования.
 * Использует Mediapipe для распознвания точек рук. Рисует схему на прозрачном Bitmap.
 * Иммитирует распознавание текста: каждые n кадров добавляет к распознанному тексту по одному
 * слову из заранее заданной строки.
 */
public class GestureAnalyzer implements IGestureAnalyzer {

    private static final String HAND_LANDMARKER_MODEL_PATH = "hand_landmarker.task";

    private final HandLandmarker handLandmarker;
    private Consumer<Bitmap> previewChangeCallback;
    private Consumer<String> textChangeCallback;

    // Запястье (wrist)
    private static final int WRIST_INDEX = 0;

    // Большой палец (thumb)
    private static final int THUMB_CMC_INDEX = 1;  // CMC (основание большого пальца, у запястья)
    public static final int THUMB_MCP_INDEX = 2;  // MCP (первая фаланга)
    public static final int THUMB_IP_INDEX = 3;  // IP (вторая фаланга)
    private static final int THUMB_TIP_INDEX = 4;  // TIP (кончик)

    // Указательный палец (index)
    private static final int INDEX_FINGER_MCP_INDEX = 5;  // MCP (основание пальца)
    public static final int INDEX_FINGER_PIP_INDEX = 6;   // PIP (средняя фаланга)
    public static final int INDEX_FINGER_DIP_INDEX = 7;   // DIP (предпоследняя)
    private static final int INDEX_FINGER_TIP_INDEX = 8;  // TIP (кончик)

    // Средний палец (middle)
    private static final int MIDDLE_FINGER_MCP_INDEX = 9;  // MCP (основание пальца)
    public static final int MIDDLE_FINGER_PIP_INDEX = 10;   // PIP (средняя фаланга)
    public static final int MIDDLE_FINGER_DIP_INDEX = 11;   // DIP (предпоследняя)
    private static final int MIDDLE_FINGER_TIP_INDEX = 12;  // TIP (кончик)

    // Безымянный палец (ring)
    private static final int RING_FINGER_MCP_INDEX = 13;  // MCP (основание пальца)
    public static final int RING_FINGER_PIP_INDEX = 14;   // PIP (средняя фаланга)
    public static final int RING_FINGER_DIP_INDEX = 15;   // DIP (предпоследняя)
    private static final int RING_FINGER_TIP_INDEX = 16;  // TIP (кончик)

    // Мизинец (pinky)
    private static final int PINKY_MCP_INDEX = 17;  // MCP (основание пальца)
    public static final int PINKY_PIP_INDEX = 18;   // PIP (средняя фаланга)
    public static final int PINKY_DIP_INDEX = 19;   // DIP (предпоследняя)
    private static final int PINKY_TIP_INDEX = 20;  // TIP (кончик)

    public GestureAnalyzer(Context context) {
        handLandmarker = HandLandmarker.createFromOptions(context, createHandLandmarkerOptions());
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
    public void analyze(Bitmap frame, float frameRotation) {
        HandLandmarkerResult result;

        try {
            MPImage mpImage = new BitmapImageBuilder(frame).build();
            result = handLandmarker.detect(mpImage);
        } catch (Exception e) {
            return;
        }

        if (previewChangeCallback != null) {
            Bitmap analyzePreview = drawSkeleton(result, frame.getWidth(), frame.getHeight());
            previewChangeCallback.accept(analyzePreview);
        }

        if (textChangeCallback != null) {
            result.landmarks().forEach(landmark -> {
                String gestureName = analyzeLandmarks(landmark);
                textChangeCallback.accept(gestureName);
            });
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

        result.landmarks().forEach(landmark -> {
            String gestureName = analyzeLandmarks(landmark);
//            Log.d("tatatest", gestureName + "");
            textChangeCallback.accept(gestureName);
        });
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

    // Распознавание

    private boolean isFingerExtended(float landmarkTipY, float landmarkMcpY, float fingerExtendedThreshold) {
        return landmarkTipY < landmarkMcpY - fingerExtendedThreshold;
    }

    private String analyzeLandmarks(List<NormalizedLandmark> landmarks) {
        Log.d("land", landmarks + "");
        if (landmarks == null || landmarks.size() < 21) {
            return "unknown";
        }

// Запястье (wrist)
        float wristX = landmarks.get(WRIST_INDEX).x();  // X координата запястья
        float wristY = landmarks.get(WRIST_INDEX).y();  // Y координата запястья

// Большой палец (thumb)
        float thumbCmcX = landmarks.get(THUMB_CMC_INDEX).x();  // X координата основания большого пальца (CMC)
        float thumbCmcY = landmarks.get(THUMB_CMC_INDEX).y();  // Y координата основания большого пальца (CMC)

        float thumbMcpX = landmarks.get(THUMB_MCP_INDEX).x();  // X координата MCP большого пальца
        float thumbMcpY = landmarks.get(THUMB_MCP_INDEX).y();  // Y координата MCP большого пальца

        float thumbIpX = landmarks.get(THUMB_IP_INDEX).x();    // X координата IP большого пальца
        float thumbIpY = landmarks.get(THUMB_IP_INDEX).y();    // Y координата IP большого пальца

        float thumbTipX = landmarks.get(THUMB_TIP_INDEX).x();  // X координата кончика большого пальца
        float thumbTipY = landmarks.get(THUMB_TIP_INDEX).y();  // Y координата кончика большого пальца

// Указательный палец (index)
        float indexFingerMcpX = landmarks.get(INDEX_FINGER_MCP_INDEX).x();  // X координата MCP указательного пальца
        float indexFingerMcpY = landmarks.get(INDEX_FINGER_MCP_INDEX).y();  // Y координата MCP указательного пальца

        float indexFingerPipX = landmarks.get(INDEX_FINGER_PIP_INDEX).x();  // X координата PIP указательного пальца
        float indexFingerPipY = landmarks.get(INDEX_FINGER_PIP_INDEX).y();  // Y координата PIP указательного пальца

        float indexFingerDipX = landmarks.get(INDEX_FINGER_DIP_INDEX).x();  // X координата DIP указательного пальца
        float indexFingerDipY = landmarks.get(INDEX_FINGER_DIP_INDEX).y();  // Y координата DIP указательного пальца

        float indexFingerTipX = landmarks.get(INDEX_FINGER_TIP_INDEX).x();  // X координата кончика указательного пальца
        float indexFingerTipY = landmarks.get(INDEX_FINGER_TIP_INDEX).y();  // Y координата кончика указательного пальца

// Средний палец (middle)
        float middleFingerMcpX = landmarks.get(MIDDLE_FINGER_MCP_INDEX).x();  // X координата MCP среднего пальца
        float middleFingerMcpY = landmarks.get(MIDDLE_FINGER_MCP_INDEX).y();  // Y координата MCP среднего пальца

        float middleFingerPipX = landmarks.get(MIDDLE_FINGER_PIP_INDEX).x();  // X координата PIP среднего пальца
        float middleFingerPipY = landmarks.get(MIDDLE_FINGER_PIP_INDEX).y();  // Y координата PIP среднего пальца

        float middleFingerDipX = landmarks.get(MIDDLE_FINGER_DIP_INDEX).x();  // X координата DIP среднего пальца
        float middleFingerDipY = landmarks.get(MIDDLE_FINGER_DIP_INDEX).y();  // Y координата DIP среднего пальца

        float middleFingerTipX = landmarks.get(MIDDLE_FINGER_TIP_INDEX).x();  // X координата кончика среднего пальца
        float middleFingerTipY = landmarks.get(MIDDLE_FINGER_TIP_INDEX).y();  // Y координата кончика среднего пальца

// Безымянный палец (ring)
        float ringFingerMcpX = landmarks.get(RING_FINGER_MCP_INDEX).x();  // X координата MCP безымянного пальца
        float ringFingerMcpY = landmarks.get(RING_FINGER_MCP_INDEX).y();  // Y координата MCP безымянного пальца

        float ringFingerPipX = landmarks.get(RING_FINGER_PIP_INDEX).x();  // X координата PIP безымянного пальца
        float ringFingerPipY = landmarks.get(RING_FINGER_PIP_INDEX).y();  // Y координата PIP безымянного пальца

        float ringFingerDipX = landmarks.get(RING_FINGER_DIP_INDEX).x();  // X координата DIP безымянного пальца
        float ringFingerDipY = landmarks.get(RING_FINGER_DIP_INDEX).y();  // Y координата DIP безымянного пальца

        float ringFingerTipX = landmarks.get(RING_FINGER_TIP_INDEX).x();  // X координата кончика безымянного пальца
        float ringFingerTipY = landmarks.get(RING_FINGER_TIP_INDEX).y();  // Y координата кончика безымянного пальца

// Мизинец (pinky)
        float pinkyFingerMcpX = landmarks.get(PINKY_MCP_INDEX).x();  // X координата MCP мизинца
        float pinkyFingerMcpY = landmarks.get(PINKY_MCP_INDEX).y();  // Y координата MCP мизинца

        float pinkyFingerPipX = landmarks.get(PINKY_PIP_INDEX).x();  // X координата PIP мизинца
        float pinkyFingerPipY = landmarks.get(PINKY_PIP_INDEX).y();  // Y координата PIP мизинца

        float pinkyFingerDipX = landmarks.get(PINKY_DIP_INDEX).x();  // X координата DIP мизинца
        float pinkyFingerDipY = landmarks.get(PINKY_DIP_INDEX).y();  // Y координата DIP мизинца

        float pinkyTipX = landmarks.get(PINKY_TIP_INDEX).x();        // X координата кончика мизинца
        float pinkyTipY = landmarks.get(PINKY_TIP_INDEX).y();        // Y координата кончика мизинца



        float fingerExtendedThreshold = 0.05f;

        // Проверяем, вытянуты ли пальцы
        boolean isThumbExtended = isFingerExtended(thumbTipY, landmarks.get(THUMB_CMC_INDEX).y(), fingerExtendedThreshold);
        boolean isIndexExtended = isFingerExtended(indexFingerTipY, landmarks.get(INDEX_FINGER_MCP_INDEX).y(), fingerExtendedThreshold);
        boolean isMiddleExtended = isFingerExtended(middleFingerTipY, landmarks.get(MIDDLE_FINGER_MCP_INDEX).y(), fingerExtendedThreshold);
        boolean isRingExtended = isFingerExtended(ringFingerTipY, landmarks.get(RING_FINGER_MCP_INDEX).y(), fingerExtendedThreshold);
        boolean isPinkyExtended = isFingerExtended(pinkyTipY, landmarks.get(PINKY_MCP_INDEX).y(), fingerExtendedThreshold);

        boolean isAllExtended = isThumbExtended && isIndexExtended && isMiddleExtended && isRingExtended && isPinkyExtended;



        // 1. Проверка "Окей" норм работает
        float okCircleThreshold = 0.05f;
        double thumbIndexDistance = Math.sqrt(
                Math.pow(thumbTipX - indexFingerTipX, 2) +
                        Math.pow(thumbTipY - indexFingerTipY, 2)
        );

        // Определим ориентацию руки: вправо (ладонь вправо) или влево (ладонь влево)
        // Сравним координаты запястья и среднего MCP
        boolean isFacingRight = wristX < middleFingerMcpX;
        boolean isFacingLeft = wristX > middleFingerMcpX;

        boolean isMiddleStraight, isRingStraight, isPinkyStraight;

        if (isFacingRight) {
            // Вытянутость по Y
            isMiddleStraight = middleFingerTipY < middleFingerMcpY - fingerExtendedThreshold;
            isRingStraight = ringFingerTipY < ringFingerMcpY - fingerExtendedThreshold;
            isPinkyStraight = pinkyTipY < pinkyFingerMcpY - fingerExtendedThreshold;
        } else {
            // Вытянутость по X для левой стороны
            isMiddleStraight = middleFingerTipX < middleFingerMcpX - fingerExtendedThreshold;
            isRingStraight = ringFingerTipX < ringFingerMcpX - fingerExtendedThreshold;
            isPinkyStraight = pinkyTipX < pinkyFingerMcpX - fingerExtendedThreshold;
        }

        if (thumbIndexDistance < okCircleThreshold &&
                isMiddleStraight && isRingStraight && isPinkyStraight) {
            return "Окей";
        }

        // 2. Проверяем "Кулак" — все пальцы согнуты (tip ближе к ладони)
        float foldedThreshold = 0.04f;

        boolean isThumbFolded = thumbTipY > thumbIpY - foldedThreshold;
        boolean isIndexFolded = indexFingerTipY > indexFingerPipY - foldedThreshold;
        boolean isMiddleFolded = middleFingerTipY > middleFingerPipY - foldedThreshold;
        boolean isRingFolded = ringFingerTipY > ringFingerPipY - foldedThreshold;
        boolean isPinkyFolded = pinkyTipY > pinkyFingerPipY - foldedThreshold;

        // Дополнительно: убедимся, что кончики всех пальцев находятся близко к центру ладони
        float palmCenterY = wristY;

        boolean isThumbNearPalm = Math.abs(thumbTipY - palmCenterY) < 0.15f;
        boolean isIndexNearPalm = Math.abs(indexFingerTipY - palmCenterY) < 0.15f;
        boolean isMiddleNearPalm = Math.abs(middleFingerTipY - palmCenterY) < 0.15f;
        boolean isRingNearPalm = Math.abs(ringFingerTipY - palmCenterY) < 0.15f;
        boolean isPinkyNearPalm = Math.abs(pinkyTipY - palmCenterY) < 0.15f;

        if (isThumbFolded && isIndexFolded && isMiddleFolded && isRingFolded && isPinkyFolded &&
                isThumbNearPalm && isIndexNearPalm && isMiddleNearPalm && isRingNearPalm && isPinkyNearPalm) {
            return "Кулак";
        }

        // 3. Распознавание "рок"
        boolean isIndexExtended2 = indexFingerTipY < indexFingerPipY;
        boolean isPinkyExtended2 = pinkyTipY < pinkyFingerPipY;

        boolean isMiddleFoldedRock = middleFingerTipY > middleFingerPipY;
        boolean isRingFoldedRock = ringFingerTipY > ringFingerPipY;
        boolean isThumbFoldedRock = thumbTipY > thumbIpY;

        if (isIndexExtended2 && isPinkyExtended2 && isMiddleFoldedRock && isRingFoldedRock && isThumbFoldedRock) {
            return "Рок";
        }

        // 4. Распознавание "указание направления"
        boolean isIndexExtendedDirection = Math.abs(indexFingerTipY - indexFingerPipY) > 0.03f;  // Проверка на выпрямленный указательный палец

        boolean isThumbFoldedDirection = thumbTipY > thumbIpY + 0.02f;  // Проверка на согнутый большой палец
        boolean isMiddleFoldedDirection = middleFingerTipY > middleFingerPipY + 0.02f;  // Проверка на согнутый средний палец
        boolean isRingFoldedDirection = ringFingerTipY > ringFingerPipY + 0.02f;  // Проверка на согнутый безымянный палец
        boolean isPinkyFoldedDirection = pinkyTipY > pinkyFingerPipY + 0.02f;  // Проверка на согнутый мизинец

        if (isIndexExtendedDirection && isThumbFoldedDirection &&
                isMiddleFoldedDirection && isRingFoldedDirection && isPinkyFoldedDirection) {
            return "Указание направления";
        }

        //5.Проверяем "V жест"
        float fingerDistanceThreshold = 0.1f;

        isIndexExtended = indexFingerTipY < indexFingerMcpY - fingerExtendedThreshold;
        isMiddleExtended = middleFingerTipY < middleFingerMcpY - fingerExtendedThreshold;

        double distance = Math.sqrt(Math.pow(indexFingerTipX - middleFingerTipX, 2) + Math.pow(indexFingerTipY-middleFingerTipY, 2));

        boolean isRingPinkyFolded = ringFingerTipY > ringFingerMcpY && pinkyTipY > pinkyFingerMcpY;

        if (isIndexExtended && isMiddleExtended && distance > fingerDistanceThreshold && isRingPinkyFolded){
            return "V жест";
        }

        // 6. Распознавание "пистолет"
        boolean isIndexExtendedGun = indexFingerTipY < indexFingerPipY - 0.05f;
        boolean isThumbExtendedGun = Math.abs(thumbTipX - thumbIpX) > 0.05f;

        boolean isMiddleFoldedGun = middleFingerTipY > middleFingerPipY + 0.02f;
        boolean isRingFoldedGun = ringFingerTipY > ringFingerPipY + 0.02f;
        boolean isPinkyFoldedGun = pinkyTipY > pinkyFingerPipY + 0.02f;

        float[] indexVec = {
                indexFingerTipX - indexFingerMcpX,
                indexFingerTipY - indexFingerMcpY
        };

        float[] thumbVec = {
                thumbTipX - thumbMcpX,
                thumbTipY - thumbMcpY
        };

        float dot = indexVec[0] * thumbVec[0] + indexVec[1] * thumbVec[1];
        float indexLen = (float)Math.sqrt(indexVec[0]*indexVec[0] + indexVec[1]*indexVec[1]);
        float thumbLen = (float)Math.sqrt(thumbVec[0]*thumbVec[0] + thumbVec[1]*thumbVec[1]);
        float angleCos = dot / (indexLen * thumbLen);
        float angleDeg = (float)Math.toDegrees(Math.acos(angleCos));

        if (isIndexExtendedGun && isThumbExtendedGun &&
                isMiddleFoldedGun && isRingFoldedGun && isPinkyFoldedGun &&
                angleDeg > 70 && angleDeg < 110) {
            return "Пистолет";
        }


        // 7. Распознавание "звонок"
        boolean isThumbExtendedCall = Math.abs(thumbTipX - thumbIpX) > 0.04f || Math.abs(thumbTipY - thumbIpY) > 0.04f;
        boolean isPinkyExtendedCall = pinkyTipY < pinkyFingerPipY - 0.03f;

        boolean isIndexFoldedCall = indexFingerTipY > indexFingerPipY + 0.02f;
        boolean isMiddleFoldedCall = middleFingerTipY > middleFingerPipY + 0.02f;
        boolean isRingFoldedCall = ringFingerTipY > ringFingerPipY + 0.02f;

        if (isThumbExtendedCall && isPinkyExtendedCall &&
                isIndexFoldedCall && isMiddleFoldedCall && isRingFoldedCall) {
            return "Звонок";
        }

        // 8. Проверка "большой палец вниз"
        float foldedThreshold2 = 0.04f;

        // Расстояние от кончиков до MCP остальных пальцев
        boolean isIndexFolded2 = Math.abs(indexFingerTipY - indexFingerMcpY) < foldedThreshold2;
        boolean isMiddleFolded2 = Math.abs(middleFingerTipY - middleFingerMcpY) < foldedThreshold2;
        boolean isRingFolded2 = Math.abs(ringFingerTipY - ringFingerMcpY) < foldedThreshold2;
        boolean isPinkyFolded2 = Math.abs(pinkyTipY - pinkyFingerMcpY) < foldedThreshold2;

        // Направление руки
        boolean isFacingRight2 = wristX < middleFingerMcpX;
        boolean isFacingLeft2 = wristX > middleFingerMcpX;

        // Проверка, что большой палец направлен вниз
        boolean isThumbDown;
        if (isFacingRight2 || isFacingLeft2) {
            // Учитываем Y: кончик большого пальца должен быть ниже основания
            isThumbDown = thumbTipY > thumbIpY + 0.02f; // немного с запасом
        } else {
            isThumbDown = false;
        }

        if (isThumbDown && isIndexFolded2 && isMiddleFolded2 && isRingFolded2 && isPinkyFolded2) {
            return "Большой палец вниз";
        }

        // 9. Проверяем "большой палец вверх"
        boolean isOnlyThumbExtended = isThumbExtended &&
                !isIndexExtended && !isMiddleExtended && !isRingExtended && !isPinkyExtended;

        // Проверка направления вверх: координата Y большого пальца должна быть меньше Y запястья
        boolean isThumbUpwards = thumbTipY < wristY;

        if (isOnlyThumbExtended && isThumbUpwards) {
            return "большой палец вверх";
        }

        // 10.Проверяем "Ладонь"
        float threshold = 0.6f;
        if (isAllExtended &&
                Math.abs(indexFingerTipY - wristY) < threshold &&
                Math.abs(middleFingerTipY - wristY) < threshold &&
                Math.abs(ringFingerTipY - wristY) < threshold &&
                Math.abs(pinkyTipY - wristY) < threshold) {
            return "Ладонь";
        }

        return "unknown";
    }
}
