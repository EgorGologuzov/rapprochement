package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelInputSoundVF extends ViewFactoryBase {
    private final RecordMultiMode model;

    public PanelInputSoundVF(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.panel_input_sound, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    model.deactivatePanel();
                    HistoryMain.shared.remove(model);
                });

        return view;
    }
}
