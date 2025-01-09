package com.nti.rapprochement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;

public class ViewsUtils {
    public static View inflateLayout(Context context, int id) {
        return LayoutInflater
            .from(context)
            .inflate(id, null);
    }
}
