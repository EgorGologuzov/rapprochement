package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.components.ViewFactoryBase;

public class ModelBase {

    private ViewFactoryBase viewFactory;

    public ViewFactoryBase getViewFactory() {
        return viewFactory;
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

    public void destroySelf() {}
}
