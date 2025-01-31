package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputGestureVF;
import com.nti.rapprochement.components.PanelInputSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelInputSound extends PanelBase {
    public PanelInputSound(RecordMultiMode model) {
        super(new PanelInputSoundVF(model));
    }
}
