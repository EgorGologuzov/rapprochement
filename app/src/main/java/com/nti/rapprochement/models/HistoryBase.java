package com.nti.rapprochement.models;

import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.components.HistoryVF;
import com.nti.rapprochement.components.ViewFactoryBase;

import java.util.ArrayList;

public class HistoryBase {
    final protected ArrayList<RecordBase> records = new ArrayList<>();

    final protected RecordAdapter adapter = new RecordAdapter(records);

    public void add(RecordBase record) {
        adapter.addItem(record);
    }

    public void remove(RecordBase record) {
        adapter.removeItem(record);
    }

    public ViewFactoryBase getViewFactory() {
        return new HistoryVF(this);
    }

    public RecordAdapter getAdapter() {
        return adapter;
    }
}
