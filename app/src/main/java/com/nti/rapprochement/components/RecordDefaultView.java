package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordDefaultView {
    public static View create(ViewGroup parent, @StringRes int text) {
        FrameLayout view = (FrameLayout) ViewsUtils.createView(R.layout.record_base, parent);
        TextView textView = new TextView(parent.getContext());
        textView.setText(text);
        view.addView(textView);
        return view;
    }
}
