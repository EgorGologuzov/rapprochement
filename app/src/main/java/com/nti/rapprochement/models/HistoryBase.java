package com.nti.rapprochement.models;

import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.components.HistoryView;

import java.util.ArrayList;

public class HistoryBase {
    final private ArrayList<RecordBase> records = new ArrayList<>();
    final private RecordAdapter adapter = new RecordAdapter(records);

    public void add(RecordBase record) {
        adapter.addItem(record);
    }

    public void remove(RecordBase record) {
        adapter.removeItem(record);
    }
    
    public RecyclerView getView() {
        return HistoryView.create(adapter);
    }
}
