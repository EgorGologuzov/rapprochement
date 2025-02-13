package com.nti.rapprochement.data;

import android.content.res.Resources;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

import com.nti.rapprochement.MainActivity;

public class Res {

    private static Resources res;
    private static MainActivity mainActivity;

    public static void init(MainActivity mainActivity) {
        res = mainActivity.getResources();
        Res.mainActivity = mainActivity;
    }

    public static String str(@StringRes int id) {
        return res.getString(id);
    }

    @ColorInt
    public static int color(@ColorRes int id) {
        return res.getColor(id, mainActivity.getTheme());
    }
}
