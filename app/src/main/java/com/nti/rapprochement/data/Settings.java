package com.nti.rapprochement.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class Settings {
    private static final String APP_PREFERENCES = "app_preferences";
    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        setTheme(getTheme());
    }


    private static final String THEME = "theme";

    public static boolean getTheme() {
        return preferences.getBoolean(THEME, false);
    }

    public static void setTheme(boolean isDarkMode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(THEME, isDarkMode);
        editor.apply();

        AppCompatDelegate.setDefaultNightMode(
            isDarkMode
                ? AppCompatDelegate.MODE_NIGHT_NO
                : AppCompatDelegate.MODE_NIGHT_YES);
    }
}
