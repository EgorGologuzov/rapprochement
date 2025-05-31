package com.nti.rapprochement.utils;

import androidx.annotation.DrawableRes;

import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.models.RecordCall;

public class Convert {

    public static String darkModeToString(boolean isDarkMode) {
        return isDarkMode ? Res.str(R.string.theme_dark) : Res.str(R.string.theme_light);
    }

    public static String fontSizeToString(Settings.FontSize fontSize) {
        switch (fontSize) {
            case Normal: return Res.str(R.string.font_normal);
            case Big: return Res.str(R.string.font_big);
            case VeryBig: return Res.str(R.string.font_very_big);
            default: throw new IllegalArgumentException();
        }
    }

    public static int fontSizeToStyleId(Settings.FontSize fontSize) {
        switch (fontSize) {
            case Normal: return R.style.Theme_Rapprochement_FontNormal;
            case Big: return R.style.Theme_Rapprochement_FontBig;
            case VeryBig: return R.style.Theme_Rapprochement_FontVeryBig;
            default: throw new IllegalArgumentException();
        }
    }

    public static Settings.FontSize stringToFontSize(String variant) {
        if (variant.equals(Res.str(R.string.font_normal))) {
            return Settings.FontSize.Normal;
        } else if (variant.equals(Res.str(R.string.font_big))) {
            return Settings.FontSize.Big;
        } else if (variant.equals(Res.str(R.string.font_very_big))) {
            return Settings.FontSize.VeryBig;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String[] getFontSizeStringVariants() {
        return new String[] {
                Res.str(R.string.font_normal),
                Res.str(R.string.font_big),
                Res.str(R.string.font_very_big)
        };
    }

    @DrawableRes
    public static int recordCallStatusToDrawableId(RecordCall.Status status) {
        switch (status) {
            case Gesture: return R.drawable.source_type_gesture;
            case Sound: return R.drawable.source_type_sound;
            case Text: return R.drawable.source_type_text;
            case Success: return R.drawable.source_type_success;
            case Error: return R.drawable.source_type_error;
            case Other: return R.drawable.source_type_other;
            default: throw new IllegalArgumentException();
        }
    }
}
