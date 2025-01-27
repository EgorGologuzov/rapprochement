package com.nti.rapprochement.models;

import android.view.View;

import com.nti.rapprochement.components.PanelInputTextView;

public class PanelInputText extends PanelBase {
    private RecordMultiMode model;

    public PanelInputText(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public View getView() {
        return PanelInputTextView.create(model);
    }
}
