package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputGestureVF;
import com.nti.rapprochement.components.RModeInputSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeInputSound extends RModeBase {
    public RModeInputSound(RecordMultiMode model) {
        super(model);
        setViewFactory(new RModeInputSoundVF(model));
        setPanel(new PanelInputSound(model));
    }
}
