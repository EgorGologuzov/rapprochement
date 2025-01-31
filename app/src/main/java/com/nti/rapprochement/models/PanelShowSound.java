package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputGestureVF;
import com.nti.rapprochement.components.PanelShowSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelShowSound extends PanelBase {
    public PanelShowSound(RecordMultiMode model) {
        super(new PanelShowSoundVF(model));
    }
}
