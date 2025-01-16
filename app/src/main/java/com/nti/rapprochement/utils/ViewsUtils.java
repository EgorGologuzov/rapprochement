package com.nti.rapprochement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewsUtils {
    public static View createView(int layoutId, Context context) {
        return LayoutInflater
            .from(context)
            .inflate(layoutId, null);
    }

    public static View createView(int layoutId, ViewGroup root) {
        return LayoutInflater
                .from(root.getContext())
                .inflate(layoutId, root, false);
    }

    public static View createView(int layoutId, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater
                .from(root.getContext())
                .inflate(layoutId, root, attachToRoot);
    }
}
