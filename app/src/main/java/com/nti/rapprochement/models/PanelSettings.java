package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.PanelBaseVM;
import com.nti.rapprochement.viewmodels.PanelSettingsVM;

public class PanelSettings extends PanelBase {
    @Override
    public PanelBaseVM createViewModel() {
        return new PanelSettingsVM();
    }
}
