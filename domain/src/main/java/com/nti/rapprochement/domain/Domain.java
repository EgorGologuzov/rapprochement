package com.nti.rapprochement.domain;

import android.content.Context;

import com.nti.rapprochement.domain.contracts.IGestureAnalyzer;
import com.nti.rapprochement.domain.contracts.IGestureStorage;
import com.nti.rapprochement.domain.implementations.FakeGestureStorage;
import com.nti.rapprochement.domain.implementations.GestureAnalyzer;

/**
 * Класс для предоставления фронтенду анализаторов данных из бэкенда
 */
public class Domain {
    /**
     * Метод для получения экземпляра IGestureAnalyzer. Изначально установлена реализация
     * FakeGestureAnalyzer, которая не распознает жести, а только иммитирует распознавание.
     * Когда бэкенд команда реализует настоящий анализатор, для интеграции его в приложение
     * достаточно будет заменить на него текущий FakeGestureAnalyzer и пересобрать приложение.
     * @param context
     * Android context в котором создается анализатор
     * @return
     * Реализация интерфейса, которую мы хотим использовать в приложении на данный момент
     */
    public static IGestureAnalyzer getGestureAnalyzer(Context context) {
        return new GestureAnalyzer(context);
    }

    public static IGestureStorage getGestureStorage(Context context) {
        return new FakeGestureStorage(context);
    }
}
