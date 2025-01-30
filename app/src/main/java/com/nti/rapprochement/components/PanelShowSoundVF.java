package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.RModeInputText;
import com.nti.rapprochement.models.RModeShowText;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelShowSoundVF extends ViewFactoryBase {
    private final RecordMultiMode model;

    public PanelShowSoundVF(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.panel_show_sound, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    RModeShowText mode = new RModeShowText(model);
                    model.setMode(mode);
                    model.activatePanel();
                    model.update();
                });

        return view;
    }
}
