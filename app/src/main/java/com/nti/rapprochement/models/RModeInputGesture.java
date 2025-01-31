package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputGestureVF;
import com.nti.rapprochement.components.RModeInputTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeInputGesture extends RModeBase {
    public RModeInputGesture(RecordMultiMode model) {
        super(model);
        setViewFactory(new RModeInputGestureVF(model));
        setPanel(new PanelInputGesture(model));
    }
}
