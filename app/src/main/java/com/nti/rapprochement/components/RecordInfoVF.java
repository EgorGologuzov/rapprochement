package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordInfoVF extends ViewFactoryBase {
    @Override
    public View create(ViewGroup parent) {
        return createAndRegister(R.layout.record_info, parent);
    }
}
