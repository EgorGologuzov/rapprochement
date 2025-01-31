package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputGestureVF;
import com.nti.rapprochement.components.RModeShowGestureVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeShowGesture extends RModeBase {
    public RModeShowGesture(RecordMultiMode model) {
        super(model);
        setViewFactory(new RModeShowGestureVF(model));
        setPanel(new PanelShowGesture(model));
    }
}
