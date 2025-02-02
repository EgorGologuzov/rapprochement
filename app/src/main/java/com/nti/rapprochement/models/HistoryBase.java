package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.utils.RecordAdapter;
import com.nti.rapprochement.components.HistoryVF;
import com.nti.rapprochement.components.ViewFactoryBase;

import java.util.ArrayList;

public class HistoryBase {
    private final RecordAdapter adapter = new RecordAdapter(new ArrayList<>());

    private ViewFactoryBase viewFactory = new HistoryVF(this);

    public void add(RecordBase record) {
        adapter.addItem(record);
    }

    public void remove(RecordBase record) {
        adapter.removeItem(record);
    }

    public RecordAdapter getAdapter() {
        return adapter;
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
