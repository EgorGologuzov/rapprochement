package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.RecordDefaultView;
import com.nti.rapprochement.utils.ViewsUtils;

public class RModeBase {
    public View getView(ViewGroup parent, RecordMultiMode model) {
        return RecordDefaultView.create(parent, R.string.rmode_view_not_set);
    }

    public PanelBase getPanel(RecordMultiMode model) {
        return null;
    }
}
