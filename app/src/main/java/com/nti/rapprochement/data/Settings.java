package com.nti.rapprochement.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.nti.rapprochement.App;
import com.nti.rapprochement.MainActivity;
import com.nti.rapprochement.R;

public class Settings {


    // Инициализация
    private static final String APP_PREFERENCES = "app_preferences";
    private static SharedPreferences preferences;

    public static void init(MainActivity mainActivity) {
        preferences = mainActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    // Ночная/Дневная тема
    private static final String THEME = "theme";

    public static boolean getDarkMode() {
        return preferences.getBoolean(THEME, false);
    }

    public static void setDarkMode(boolean isDarkMode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(THEME, isDarkMode);
        editor.apply();

        AppCompatDelegate.setDefaultNightMode(
                isDarkMode
                        ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO);
    }


    // Размер шрифта
    private static final String FONT_SIZE = "font_size";

    public enum FontSize { Normal, Big, VeryBig }

    public static FontSize getFontSize() {
        return FontSize.valueOf(preferences.getString(FONT_SIZE, FontSize.Normal.name()));
    }

    public static void setFontSize(FontSize value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FONT_SIZE, value.name());
        editor.apply();
        App.current.recreateMainActivity();
    }


    // Локализация
    private static final String LOCALE = "locale";

    public static String getLocale() {
        return preferences.getString(LOCALE, "ru");
    }

    public static void setLocale(String locale) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FONT_SIZE, locale);
        editor.apply();
    }


    // Последнее направление камеры
    private static final String LAST_CAMERA_FACING = "last_camera_facing";

    public enum CameraFacing { Back, Front }

    public static CameraFacing getLastCameraFacing() {
        return CameraFacing.valueOf(preferences.getString(LAST_CAMERA_FACING, CameraFacing.Back.name()));
    }

    public static void setLastCameraFacing(CameraFacing value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_CAMERA_FACING, value.name());
        editor.apply();
    }


    // Время автоматического заврешения рапознавания жестов
    private static final String GESTURE_RECOGNIZE_TIMEOUT = "gesture_recognize_timeout";

    public static int getGestureRecognizeTimeout() {
        return preferences.getInt(GESTURE_RECOGNIZE_TIMEOUT, 120);
    }

    public static void setGestureRecognizeTimeout(int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GESTURE_RECOGNIZE_TIMEOUT, value);
        editor.apply();
    }


    // Счетчик идентификаторов записей вызовов
    private static final String RECORD_CALL_ID_COUNTER = "record_call_id_counter";

    private static int getIdCounter() {
        return preferences.getInt(RECORD_CALL_ID_COUNTER, 0);
    }

    private static void setIdCounter(int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RECORD_CALL_ID_COUNTER, value);
        editor.apply();
    }

    public static int getNextId() {
        int currentId = getIdCounter();
        setIdCounter(currentId + 1);
        return currentId;
    }


    // Настройки режима добавления жеста
    private static final String ADD_GESTURE_MODE_SNAPSHOT_TIMING = "add_gesture_mode_snapshot_timing";

    public static int getAddGestureModeSnapshotTiming() {
        return preferences.getInt(ADD_GESTURE_MODE_SNAPSHOT_TIMING, 3);
    }

    private static final String ADD_GESTURE_MODE_SNAPSHOTS_MIN_COUNT = "add_gesture_mode_snapshots_min_count";

    public static int getAddGestureModeSnapshotsMinCount() {
        return preferences.getInt(ADD_GESTURE_MODE_SNAPSHOTS_MIN_COUNT, 10);
    }

    private static final String ADD_GESTURE_MODE_SNAPSHOTS_MAX_COUNT = "add_gesture_mode_snapshots_max_count";

    public static int getAddGestureModeSnapshotsMaxCount() {
        return preferences.getInt(ADD_GESTURE_MODE_SNAPSHOTS_MAX_COUNT, 20);
    }
}
