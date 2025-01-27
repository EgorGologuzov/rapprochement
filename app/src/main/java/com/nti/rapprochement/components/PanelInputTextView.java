package com.nti.rapprochement.components;

import android.view.View;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.RModeShowText;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelInputTextView {
    public static View create(RecordMultiMode model) {
        View view = ViewsUtils.createView(R.layout.panel_input_text, App.getPanelContext());

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    model.deactivatePanel();
                    HistoryMain.shared.pop();
                    App.setKeyboardOpen(false);
                });

        view.findViewById(R.id.toText)
                .setOnClickListener(v -> {
                    RModeShowText mode = new RModeShowText();
                    model.setMode(mode);
                    model.activatePanel();
                    model.update();
                    App.setKeyboardOpen(false);
                });

        return view;
    }
}
