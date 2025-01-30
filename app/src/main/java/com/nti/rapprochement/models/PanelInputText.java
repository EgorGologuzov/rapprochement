package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelInputText extends PanelBase {
    private final RecordMultiMode model;

    public PanelInputText(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelInputTextVF(model);
    }
}
