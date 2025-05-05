package com.nti.rapprochement.domain.implementations;

import android.graphics.Bitmap;
import java.util.function.Consumer;

// КЛАСС-ПРИМЕР РЕАЛИЗАЦИИ АНАЛИЗАТОРА, ДЛЯ БЕКЕНДЕРОВ. КЛАСС НЕ ИСПОЛЗУЕТСЯ В КОДЕ!!!
public class Analyzer {

    // весь текст, распознанный с момента создания класса
    private String detectedText = "";

    // фронтенд будет устанавливать эти колбеки перед началом использования метода analyze
    private Consumer<Bitmap> previewChangeCallback;
    private Consumer<String> textChangeCallback;

    // метод анализа изображений
    // будет вызываться по мере поступления кадров с камеры, видео или картинки
    public void analyze(Bitmap frame) {
        // прозрачный Bitmap такого же размера как frame для точек и линий рук
        Bitmap drawnHands = Bitmap.createBitmap(
                frame.getWidth(),
                frame.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        // ваша логика распознавания: нарисовать drawnHands, изменить detectedText (можно асинхронно)

        // вызвать колбеки для уведомления фронтенда об изменении рисунка рук и распознанного текста
        previewChangeCallback.accept(drawnHands);
        textChangeCallback.accept(detectedText);
        // если ничего не изменилось можно не вызывать
        // можно вызывать асинхронно в других потоках
    }

    // установка колбека для обновления картинки отрисованных ручек
    public void setPreviewChangeCallback(Consumer<Bitmap> callback) {
        this.previewChangeCallback = callback;
    }

    // установка колбека для обновления респознанного текста
    public void setTextChangeCallback(Consumer<String> callback) {
        this.textChangeCallback = callback;
    }

    // утилизация анализатора, вызывается когда анализотор больше не нужен
    // используйте для закртия объектов, которые требуют ручного закрытия, например потоков
    // если нечего закрывать можно оставить пустым
    public void dispose() {}
}
