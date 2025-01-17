package com.nti.rapprochement.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;

import com.nti.rapprochement.App;
import com.nti.rapprochement.MainActivity;
import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.Event;

import java.util.function.Consumer;

public class Settings {


    // Инициализация
    private static final String APP_PREFERENCES = "app_preferences";
    private static SharedPreferences preferences;

    public static void init(MainActivity mainActivity) {
        preferences = mainActivity.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
    }


    // Ночная/Дневная тема
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
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static String themeToString(boolean isDarkMode) {
        return isDarkMode ? Res.str(R.string.theme_dark) : Res.str(R.string.theme_light);
    }


    // Размер шрифта
    private static final String FONT_SIZE = "font_size";

    public static Event<FontSize> onFontSizeChange = new Event<>();

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
        onFontSizeChange.call(value);
    }

    public static String fontSizeToString(FontSize fontSize) {
        switch (fontSize) {
            case Normal: return Res.str(R.string.font_normal);
            case Big: return Res.str(R.string.font_big);
            case VeryBig: return Res.str(R.string.font_very_big);
            default: return "";
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

    public static FontSize stringVariantToFontSize(String variant) {
        if (variant.equals(Res.str(R.string.font_normal))) {
            return FontSize.Normal;
        } else if (variant.equals(Res.str(R.string.font_big))) {
            return FontSize.Big;
        } else {
            return FontSize.VeryBig;
        }
    }

    public static String[] getFontSizeVariants() {
        return new String[] {
            Res.str(R.string.font_normal),
            Res.str(R.string.font_big),
            Res.str(R.string.font_very_big)
        };
    }


}
