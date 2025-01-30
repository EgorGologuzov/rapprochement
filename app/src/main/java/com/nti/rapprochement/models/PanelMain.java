package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelMainVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelMain extends PanelBase {
    public final static PanelMain shared = new PanelMain();

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelMainVF();
    }
}
