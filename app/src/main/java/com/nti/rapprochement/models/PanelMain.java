package com.nti.rapprochement.models;

import android.view.View;

import com.nti.rapprochement.R;
import com.nti.rapprochement.App;
import com.nti.rapprochement.components.PanelMainView;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelMain extends PanelBase {
    public final static PanelMain shared = new PanelMain();

    @Override
    public View getView() {
        return PanelMainView.create();
    }
}
