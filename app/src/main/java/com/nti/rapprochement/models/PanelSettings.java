package com.nti.rapprochement.models;

import android.view.View;

import com.nti.rapprochement.R;
import com.nti.rapprochement.App;
import com.nti.rapprochement.components.PanelSettingsView;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelSettings extends PanelBase {
    public final static PanelSettings shared = new PanelSettings();

    @Override
    public View getView() {
        return PanelSettingsView.create();
    }
}
