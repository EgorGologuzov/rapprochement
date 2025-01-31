package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputGestureVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelInputGesture extends PanelBase {
    public PanelInputGesture(RecordMultiMode model) {
        super(new PanelInputGestureVF(model));
    }
}
