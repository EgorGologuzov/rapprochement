package com.nti.rapprochement.models;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.TextViewVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeBase {
    protected final RecordMultiMode model;

    public RModeBase(RecordMultiMode model) {
        this.model = model;
    }

    public ViewFactoryBase getViewFactory() {
        return new TextViewVF(R.string.rmode_view_not_set);
    }

    public PanelBase getPanel(RecordMultiMode model) {
        return null;
    }
}
