package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.StringRes;

public class TextViewVF extends ViewFactoryBase {
    @StringRes
    private final int textId;

    public TextViewVF(@StringRes int textId) {
        this.textId = textId;
    }

    @Override
    public View create(ViewGroup parent) {
        TextView view = new TextView(parent.getContext());
        view.setText(textId);
        return null;
    }
}
