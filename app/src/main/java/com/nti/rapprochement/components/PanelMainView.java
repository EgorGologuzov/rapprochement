package com.nti.rapprochement.components;

import android.view.View;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.HistorySettings;
import com.nti.rapprochement.models.PanelSettings;
import com.nti.rapprochement.models.RModeInputText;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelMainView {
    public static View create() {
        View view = ViewsUtils.createView(R.layout.panel_main, App.getPanelContext());

        view.findViewById(R.id.settingsButton)
                .setOnClickListener(v -> App.navigate(HistorySettings.shared, PanelSettings.shared));

        view.findViewById(R.id.gestureButton)
                .setOnClickListener(v -> {
                    RecordMultiMode record = new RecordMultiMode();
                    record.activatePanel();
                    HistoryMain.shared.add(record);
                });

        view.findViewById(R.id.soundButton)
                .setOnClickListener(v -> {
                    RecordMultiMode record = new RecordMultiMode();
                    record.activatePanel();
                    HistoryMain.shared.add(record);
                });

        view.findViewById(R.id.textButton)
                .setOnClickListener(v -> {
                    RecordMultiMode record = new RecordMultiMode();
                    RModeInputText defaultMode = new RModeInputText();
                    record.setMode(defaultMode);
                    record.activatePanel();
                    HistoryMain.shared.add(record);
                });

        return view;
    }
}
