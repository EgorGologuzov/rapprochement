package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.components.RModeInputTextView;
import com.nti.rapprochement.components.RModeShowTextView;

public class RModeShowText extends RModeBase {
    @Override
    public View getView(ViewGroup parent, RecordMultiMode model) {
        return RModeShowTextView.create(parent, model);
    }
}
