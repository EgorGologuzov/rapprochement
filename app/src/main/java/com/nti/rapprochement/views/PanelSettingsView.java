package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelSettingsView {
    public static View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.panel_settings, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    App.current.navigateBack();
                });

        return view;
    }
}
