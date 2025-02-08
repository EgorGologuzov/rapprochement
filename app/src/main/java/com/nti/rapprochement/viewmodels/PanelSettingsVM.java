package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.models.PanelSettings;
import com.nti.rapprochement.views.PanelSettingsView;

public class PanelSettingsVM extends PanelBaseVM {
    @Override
    public View createView(ViewGroup parent) {
        return PanelSettingsView.create(parent);
    }
}
