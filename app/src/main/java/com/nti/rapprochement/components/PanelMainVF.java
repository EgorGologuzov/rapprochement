package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.HistorySettings;
import com.nti.rapprochement.models.PanelSettings;
import com.nti.rapprochement.models.RModeInputGesture;
import com.nti.rapprochement.models.RModeInputSound;
import com.nti.rapprochement.models.RModeInputText;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelMainVF extends ViewFactoryBase {
    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.panel_main, parent);

        view.findViewById(R.id.settingsButton)
                .setOnClickListener(v -> App.navigate(HistorySettings.current, PanelSettings.current));

        view.findViewById(R.id.gestureButton)
                .setOnClickListener(v -> {
                    RecordMultiMode record = new RecordMultiMode(RecordMultiMode.SourceType.Gesture);
                    RModeInputGesture defaultMode = new RModeInputGesture(record);
                    record.setMode(defaultMode);
                    record.activatePanel();
                    HistoryMain.current.add(record);
                });

        view.findViewById(R.id.soundButton)
                .setOnClickListener(v -> {
                    RecordMultiMode record = new RecordMultiMode(RecordMultiMode.SourceType.Sound);
                    RModeInputSound defaultMode = new RModeInputSound(record);
                    record.setMode(defaultMode);
                    record.activatePanel();
                    HistoryMain.current.add(record);
                });

        view.findViewById(R.id.textButton)
                .setOnClickListener(v -> {
                    RecordMultiMode record = new RecordMultiMode(RecordMultiMode.SourceType.Text);
                    RModeInputText defaultMode = new RModeInputText(record);
                    record.setMode(defaultMode);
                    record.activatePanel();
                    HistoryMain.current.add(record);
                });

        return view;
    }
}
