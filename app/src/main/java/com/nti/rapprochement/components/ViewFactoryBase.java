package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

public abstract class ViewFactoryBase {
    public abstract View create(ViewGroup parent);
    public void destroy(View view) {}
}
