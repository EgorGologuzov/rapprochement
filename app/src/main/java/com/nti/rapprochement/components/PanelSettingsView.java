package com.nti.rapprochement.components;

import android.view.View;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelSettingsView {
    public static View create() {
        View view = ViewsUtils.createView(R.layout.panel_settings, App.getPanelContext());
        view.findViewById(R.id.backButton).setOnClickListener(v -> App.navigateBack());
        return view;
    }
}
