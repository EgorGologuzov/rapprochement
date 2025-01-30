package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeShowTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeShowText extends RModeBase {
    public RModeShowText(RecordMultiMode model) {
        super(model);
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new RModeShowTextVF(model);
    }
}
