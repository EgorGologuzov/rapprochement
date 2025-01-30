package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputGestureVF;
import com.nti.rapprochement.components.RModeInputSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeInputSound extends RModeBase {
    public RModeInputSound(RecordMultiMode model) {
        super(model);
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new RModeInputSoundVF(model);
    }

    @Override
    public PanelBase getPanel(RecordMultiMode model) {
        return new PanelInputSound(model);
    }
}
