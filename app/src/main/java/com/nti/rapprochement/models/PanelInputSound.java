package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputGestureVF;
import com.nti.rapprochement.components.PanelInputSoundVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelInputSound extends PanelBase {
    private final RecordMultiMode model;

    public PanelInputSound(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelInputSoundVF(model);
    }
}
