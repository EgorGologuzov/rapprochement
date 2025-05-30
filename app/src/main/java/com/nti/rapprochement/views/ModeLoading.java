package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

public class ModeLoading extends RecordCallVM.Mode {
    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        FrameLayout view = (FrameLayout) ViewsUtils.createView(R.layout.mode_loading, parent);
        return view;
    }

    @Override
    public View createPanelView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        View view = ViewsUtils.createView(R.layout.panel_loading, parent);
        return view;
    }

    @Override
    public boolean hasPanel() {
        return true;
    }
}
