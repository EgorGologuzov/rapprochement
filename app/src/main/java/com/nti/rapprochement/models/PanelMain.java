package com.nti.rapprochement.models;

import android.view.View;

import com.nti.rapprochement.R;
import com.nti.rapprochement.navigation.Navigation;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelMain extends PanelBase {
    public final static PanelMain shared = new PanelMain();

    @Override
    public View getView() {
        View view = ViewsUtils.inflateLayout(R.layout.panel_main, PanelBase.getViewParent());

        view.findViewById(R.id.settingsButton).setOnClickListener(v -> Navigation.navigate(HistorySettings.shared, PanelSettings.shared));

        view.findViewById(R.id.gestureButton).setOnClickListener(v -> HistoryMain.shared.push(new RecordGesture()));
        view.findViewById(R.id.soundButton).setOnClickListener(v -> HistoryMain.shared.push(new RecordSound()));
        view.findViewById(R.id.textButton).setOnClickListener(v -> HistoryMain.shared.push(new RecordText()));

        return view;
    }
}
