package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.TextViewVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeBase extends ModelBase {
    private PanelBase panel;

    protected final RecordMultiMode model;

    public RModeBase(RecordMultiMode model) {
        this.model = model;
    }

    public PanelBase getPanel(RecordMultiMode model) {
        return panel;
    }

    protected void setPanel(PanelBase panel) {
        this.panel = panel;
    }
}
