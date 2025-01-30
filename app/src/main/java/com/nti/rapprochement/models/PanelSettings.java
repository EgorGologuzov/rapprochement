package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelSettingsVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelSettings extends PanelBase {
    public final static PanelSettings shared = new PanelSettings();

    @Override
    public ViewFactoryBase getViewFactory() {
        return new PanelSettingsVF();
    }
}
