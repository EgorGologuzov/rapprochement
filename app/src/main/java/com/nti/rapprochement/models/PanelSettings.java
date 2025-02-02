package com.nti.rapprochement.models;

import com.nti.rapprochement.components.PanelMainVF;
import com.nti.rapprochement.components.PanelSettingsVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class PanelSettings extends PanelBase {
    public final static PanelSettings current = new PanelSettings();

    public PanelSettings() {
        setViewFactory(new PanelSettingsVF());
    }
}
