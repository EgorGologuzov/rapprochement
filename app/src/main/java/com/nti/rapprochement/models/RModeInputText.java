package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeInputText extends RModeBase {
    public RModeInputText(RecordMultiMode model) {
        super(model);
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new RModeInputTextVF(model);
    }

    @Override
    public PanelBase getPanel(RecordMultiMode model) {
        return new PanelInputText(model);
    }
}
