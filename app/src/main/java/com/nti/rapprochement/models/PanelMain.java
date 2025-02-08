package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.PanelBaseVM;
import com.nti.rapprochement.viewmodels.PanelMainVM;

public class PanelMain extends PanelBase {
    @Override
    public PanelBaseVM createViewModel() {
        return new PanelMainVM();
    }
}
