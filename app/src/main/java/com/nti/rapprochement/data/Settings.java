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

    public enum FontSize { Normal, Big, VeryBig; }

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
}
