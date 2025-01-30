package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordMultiModeBaseVF extends ViewFactoryBase {
    @Override
    public View create(ViewGroup parent) {
        return ViewsUtils.createView(R.layout.record_multi_mode_base, parent);
    }
}
