package com.nti.rapprochement.models;

import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.ArrayList;

public class HistoryBase {
    private static ViewGroup viewParent;

    final private ArrayList<RecordBase> records = new ArrayList<>();
    final private RecordAdapter adapter = new RecordAdapter(records);

    private RecyclerView view;

    public static void initParent(ViewGroup viewParent) {
        HistoryBase.viewParent = viewParent;
    }

    public void push(RecordBase record) {
        records.add(record);
        adapter.notifyItemInserted(adapter.getItemCount());
        if (view != null) view.smoothScrollToPosition(adapter.getItemCount());
    }

    public void pop() {
        if (!records.isEmpty()) {
            records.remove(records.size() - 1);
        }
    }
    
    public RecyclerView getView() {
        view = (RecyclerView) ViewsUtils.inflateLayout(R.layout.history, viewParent);
        view.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(viewParent.getContext());
        layoutManager.setStackFromEnd(true);
        view.setLayoutManager(layoutManager);

        return view;
    }
}
