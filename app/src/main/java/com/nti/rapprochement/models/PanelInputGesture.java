package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputGestureVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelInputGesture extends PanelBase {
    private final RecordMultiMode model;

    public PanelInputGesture(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelInputGestureVF(model);
    }
}
