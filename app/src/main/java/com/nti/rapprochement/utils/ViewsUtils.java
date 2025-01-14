package com.nti.rapprochement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;

public class ViewsUtils {
    public static View inflateLayout(int layoutId, Context context) {
        return LayoutInflater
            .from(context)
            .inflate(layoutId, null);
    }

    public static View inflateLayout(int layoutId, ViewGroup root) {
        return LayoutInflater
                .from(root.getContext())
                .inflate(layoutId, root, false);
    }

    public static View inflateLayout(int layoutId, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater
                .from(root.getContext())
                .inflate(layoutId, root, attachToRoot);
    }
}
