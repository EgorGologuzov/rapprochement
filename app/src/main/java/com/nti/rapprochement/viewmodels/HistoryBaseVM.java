package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.utils.Event;
import com.nti.rapprochement.utils.RecordAdapter;
import com.nti.rapprochement.views.HistoryBaseView;

public abstract class HistoryBaseVM {

    private final HistoryBase model;
    private final RecordAdapter adapter;

    public final Event<Integer> onItemInserted = new Event<>();

    public HistoryBaseVM(HistoryBase model) {
        this.model = model;
        this.adapter = new RecordAdapter(model.records);
    }

    public void add(RecordBase record) {
        adapter.addItem(record);
        onItemInserted.call(model.records.indexOf(record));
    }

    public void remove(RecordBase record) {
        adapter.removeItem(record);
    }

    public void notifyItemUpdate(RecordBase item) {
        adapter.notifyItemChanged(item);
    }

    public View createView(ViewGroup parent) {
        return HistoryBaseView.create(parent, this, adapter);
    }

    public RecordBaseVM findViewModel(RecordBase model) {
        return adapter.findViewModel(model);
    }
}
