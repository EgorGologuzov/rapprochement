package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelInputTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelInputText extends PanelBase {
    public PanelInputText(RecordMultiMode model) {
        setViewFactory(new PanelInputTextVF(model));
    }
}
