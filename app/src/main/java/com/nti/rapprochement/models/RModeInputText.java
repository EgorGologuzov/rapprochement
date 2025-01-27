package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.RModeInputTextView;

public class RModeInputText extends RModeBase {
    @Override
    public View getView(ViewGroup parent, RecordMultiMode model) {
        return RModeInputTextView.create(parent, model);
    }

    @Override
    public PanelBase getPanel(RecordMultiMode model) {
        return new PanelInputText(model);
    }
}
