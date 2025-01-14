package com.nti.rapprochement.models;

import android.view.View;

import com.nti.rapprochement.R;
import com.nti.rapprochement.navigation.Navigation;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelSettings extends PanelBase {
    public final static PanelSettings shared = new PanelSettings();

    @Override
    public View getView() {
        View view = ViewsUtils.inflateLayout(R.layout.panel_settings, PanelBase.getViewParent());
        view.findViewById(R.id.backButton).setOnClickListener(v -> Navigation.navigateBack());
        return view;
    }
}
