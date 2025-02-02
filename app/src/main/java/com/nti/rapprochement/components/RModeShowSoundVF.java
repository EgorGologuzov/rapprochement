package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class RModeShowSoundVF extends ViewFactoryBase {
    private final RecordMultiMode model;

    public RModeShowSoundVF(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        View view = createAndRegister(R.layout.rmode_show_sound, parent);
        return view;
    }
}
