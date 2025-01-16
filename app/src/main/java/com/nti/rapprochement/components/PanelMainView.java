package com.nti.rapprochement.components;

import android.view.View;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.HistorySettings;
import com.nti.rapprochement.models.PanelSettings;
import com.nti.rapprochement.models.RecordGesture;
import com.nti.rapprochement.models.RecordSound;
import com.nti.rapprochement.models.RecordText;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelMainView {
    public static View create() {
        View view = ViewsUtils.createView(R.layout.panel_main, App.getPanelContext());

        view.findViewById(R.id.settingsButton).setOnClickListener(v -> App.navigate(HistorySettings.shared, PanelSettings.shared));

        view.findViewById(R.id.gestureButton).setOnClickListener(v -> HistoryMain.shared.push(new RecordGesture()));
        view.findViewById(R.id.soundButton).setOnClickListener(v -> HistoryMain.shared.push(new RecordSound()));
        view.findViewById(R.id.textButton).setOnClickListener(v -> HistoryMain.shared.push(new RecordText()));

        return view;
    }
}
