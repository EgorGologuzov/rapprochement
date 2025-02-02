package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelShowGestureVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelShowGesture extends PanelBase {
    public PanelShowGesture(RecordMultiMode model) {
        setViewFactory(new PanelShowGestureVF(model));
    }
}
