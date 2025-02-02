package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelMainVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelMain extends PanelBase {
    public final static PanelMain current = new PanelMain();

    public PanelMain() {
        setViewFactory(new PanelMainVF());
    }
}
