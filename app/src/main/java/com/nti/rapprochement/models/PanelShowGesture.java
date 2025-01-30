package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelShowGestureVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelShowGesture extends PanelBase {
    private final RecordMultiMode model;

    public PanelShowGesture(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelShowGestureVF(model);
    }
}
