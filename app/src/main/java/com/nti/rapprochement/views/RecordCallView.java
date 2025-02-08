package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordCallView {
    public static View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.record_multi_mode_base, parent);
        return view;
    }
}
