package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class RModeShowTextView {
    public static View create(ViewGroup parent, RecordMultiMode model) {
        return ViewsUtils.createView(R.layout.rmode_show_text, parent);
    }
}
