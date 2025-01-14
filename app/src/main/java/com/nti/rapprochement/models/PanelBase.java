package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

public abstract class PanelBase {
    private static ViewGroup viewParent;

    public static void initParent(ViewGroup viewParent) {
        PanelBase.viewParent = viewParent;
    }

    public static ViewGroup getViewParent() {
        return viewParent;
    }

    public abstract View getView();
}
