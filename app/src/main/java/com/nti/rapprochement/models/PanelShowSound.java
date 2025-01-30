package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputGestureVF;
import com.nti.rapprochement.components.PanelShowSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelShowSound extends PanelBase {
    private final RecordMultiMode model;

    public PanelShowSound(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelShowSoundVF(model);
    }
}
