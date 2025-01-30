package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class RModeInputGestureVF extends ViewFactoryBase {
    private final RecordMultiMode model;

    public RModeInputGestureVF(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.rmode_input_gesture, parent);
        return view;
    }
}
