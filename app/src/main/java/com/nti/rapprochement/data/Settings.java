package com.nti.rapprochement.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;

import com.nti.rapprochement.R;

import java.util.Arrays;
import java.util.function.Consumer;

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


    private static final String FONT_SIZE = "font_size";
    private static Consumer<FontSize> onFontSizeChangeHandler;

    public enum FontSize {
        Normal, Big, VeryBig;
    }

    public static FontSize getFontSize() {
        return FontSize.valueOf(preferences.getString(FONT_SIZE, FontSize.Normal.name()));
    }

    public static void setFontSize(FontSize value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FONT_SIZE, value.name());
        editor.apply();

        if (onFontSizeChangeHandler != null) {
            onFontSizeChangeHandler.accept(value);
        }
    }

    public static void setOnFontSizeChangeHandler(Consumer<FontSize> handler) { onFontSizeChangeHandler = handler; }

    public static int fontSizeToStringId(FontSize fontSize) {
        switch (fontSize) {
            case Normal: return R.string.font_normal;
            case Big: return R.string.font_big;
            case VeryBig: return R.string.font_very_big;
            default: return -1;
        }
    }

    public static int fontSizeToStyleId(FontSize fontSize) {
        switch (fontSize) {
            case Normal: return R.style.Theme_Rapprochement_FontNormal;
            case Big: return R.style.Theme_Rapprochement_FontBig;
            case VeryBig: return R.style.Theme_Rapprochement_FontVeryBig;
            default: return -1;
        }
    }

    public static FontSize stringVariantToFontSize(String variant, Resources res) {
        if (variant.equals(res.getString(R.string.font_normal))) {
            return FontSize.Normal;
        } else if (variant.equals(res.getString(R.string.font_big))) {
            return FontSize.Big;
        } else {
            return FontSize.VeryBig;
        }
    }

    public static String[] getFontSizeVariants(Resources res) {
        return new String[] {
            res.getString(R.string.font_normal),
            res.getString(R.string.font_big),
            res.getString(R.string.font_very_big)
        };
    }
}
