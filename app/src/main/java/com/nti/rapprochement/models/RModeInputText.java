package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RModeInputTextVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeInputText extends RModeBase {
    public RModeInputText(RecordMultiMode model) {
        super(model);
        setViewFactory(new RModeInputTextVF(model));
        setPanel(new PanelInputText(model));
    }
}
