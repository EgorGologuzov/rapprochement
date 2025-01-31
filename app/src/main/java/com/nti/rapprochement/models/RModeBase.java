package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.TextViewVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RModeBase {
    private ViewFactoryBase viewFactory = new TextViewVF(R.string.record_view_not_set);
    private PanelBase panel;

    protected final RecordMultiMode model;

    public RModeBase(RecordMultiMode model) {
        this.model = model;
    }

    public PanelBase getPanel(RecordMultiMode model) {
        return panel;
    }

    public void setPanel(PanelBase panel) {
        this.panel = panel;
    }

    public void setViewFactory(ViewFactoryBase viewFactory) {
        this.viewFactory = viewFactory;
    }

    public View createView(ViewGroup parent) {
        return viewFactory.create(parent);
    }

    public void destroyView(View view) {
        viewFactory.destroy(view);
    }
}
