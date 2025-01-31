package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputGestureVF;
import com.nti.rapprochement.components.RModeShowSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeShowSound extends RModeBase {
    public RModeShowSound(RecordMultiMode model) {
        super(model);
        setViewFactory(new RModeShowSoundVF(model));
        setPanel(new PanelShowSound(model));
    }
}
