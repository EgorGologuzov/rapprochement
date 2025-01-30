package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputGestureVF;
import com.nti.rapprochement.components.RModeInputTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeInputGesture extends RModeBase {
    public RModeInputGesture(RecordMultiMode model) {
        super(model);
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new RModeInputGestureVF(model);
    }

    @Override
    public PanelBase getPanel(RecordMultiMode model) {
        return new PanelInputGesture(model);
    }
}
