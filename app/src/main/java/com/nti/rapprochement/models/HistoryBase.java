package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.utils.RecordAdapter;
import com.nti.rapprochement.components.HistoryVF;
import com.nti.rapprochement.components.ViewFactoryBase;

import java.util.ArrayList;

public class HistoryBase extends ModelBase {
    private final RecordAdapter adapter = new RecordAdapter(new ArrayList<>());

    public HistoryBase() {
        setViewFactory(new HistoryVF(adapter));
    }

    public void add(RecordBase record) {
        adapter.addItem(record);
    }

    public void remove(RecordBase record) {
        adapter.removeItem(record);
        record.destroySelf();
    }
}
