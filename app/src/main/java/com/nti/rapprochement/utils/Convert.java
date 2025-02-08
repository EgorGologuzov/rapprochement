package com.nti.rapprochement.utils;

import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;

public class Convert {

    public static String darkModeToString(boolean isDarkMode) {
        return isDarkMode ? Res.str(R.string.theme_dark) : Res.str(R.string.theme_light);
    }

    public static String fontSizeToString(Settings.FontSize fontSize) {
        switch (fontSize) {
            case Normal: return Res.str(R.string.font_normal);
            case Big: return Res.str(R.string.font_big);
            case VeryBig: return Res.str(R.string.font_very_big);
            default: return "";
        }
    }

    public static int fontSizeToStyleId(Settings.FontSize fontSize) {
        switch (fontSize) {
            case Normal: return R.style.Theme_Rapprochement_FontNormal;
            case Big: return R.style.Theme_Rapprochement_FontBig;
            case VeryBig: return R.style.Theme_Rapprochement_FontVeryBig;
            default: return -1;
        }
    }

    public static Settings.FontSize stringToFontSize(String variant) {
        if (variant.equals(Res.str(R.string.font_normal))) {
            return Settings.FontSize.Normal;
        } else if (variant.equals(Res.str(R.string.font_big))) {
            return Settings.FontSize.Big;
        } else {
            return Settings.FontSize.VeryBig;
        }
    }

    public static String[] getFontSizeStringVariants() {
        return new String[] {
                Res.str(R.string.font_normal),
                Res.str(R.string.font_big),
                Res.str(R.string.font_very_big)
        };
    }
}
