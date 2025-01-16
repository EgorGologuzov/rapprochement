package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordInfoView {
    public static View create(ViewGroup parent) {
        return ViewsUtils.createView(R.layout.record_info, parent);
    }
}
