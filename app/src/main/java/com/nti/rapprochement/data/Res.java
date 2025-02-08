package com.nti.rapprochement.data;

import android.content.res.Resources;

import com.nti.rapprochement.MainActivity;

public class Res {
    private static Resources res;

    public static void init(MainActivity mainActivity) {
        res = mainActivity.getResources();
    }

    public static String str(int id) {
        return res.getString(id);
    }
}
