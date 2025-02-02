package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelSettingsVF extends ViewFactoryBase {
    @Override
    public View create(ViewGroup parent) {
        View view = createAndRegister(R.layout.panel_settings, parent);
        view.findViewById(R.id.backButton).setOnClickListener(v -> App.navigateBack());
        return view;
    }
}
