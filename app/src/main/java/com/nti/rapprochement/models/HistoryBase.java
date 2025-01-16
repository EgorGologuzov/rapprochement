package com.nti.rapprochement.models;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.components.HistoryView;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.ArrayList;

public class HistoryBase {
    final private ArrayList<RecordBase> records = new ArrayList<>();
    final private RecordAdapter adapter = new RecordAdapter(records);

    public void push(RecordBase record) {
        adapter.addItem(record);
    }

    public void pop() {
        adapter.removeLastItem();
    }
    
    public RecyclerView getView() {
        return HistoryView.create(adapter);
    }
}
