package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.models.PanelMain;
import com.nti.rapprochement.views.PanelMainView;

public class PanelMainVM extends PanelBaseVM {
    @Override
    public View createView(ViewGroup parent) {
        return PanelMainView.create(parent);
    }
}
