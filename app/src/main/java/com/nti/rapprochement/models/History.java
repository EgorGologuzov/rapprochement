package com.nti.rapprochement.models;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.ArrayList;

public class History {
    final public static History main = initHistoryMain();
    final public static History settings = initHistorySettings();

    final private ArrayList<RecordBase> records = new ArrayList<>();
    final private RecordAdapter adapter = new RecordAdapter(records);
    private RecyclerView view;

    private static History initHistoryMain() {
        History history = new History();
        RecordInfo info = new RecordInfo();
        history.push(info);
        return history;
    }

    private static History initHistorySettings() {
        History history = new History();
        RecordSettingsGroup settingsGroup1 = new RecordSettingsGroup();
        RecordSettingsGroup settingsGroup2 = new RecordSettingsGroup();
        RecordSettingsGroup settingsGroup3 = new RecordSettingsGroup();
        history.push(settingsGroup1);
        history.push(settingsGroup2);
        history.push(settingsGroup3);
        return history;
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
    
    public RecyclerView getView(Context context) {
        if (view != null) return view;

        RecyclerView newView = (RecyclerView) ViewsUtils.inflateLayout(context, R.layout.history);
        return initView(context, newView);
    }

    public RecyclerView initView(Context context, RecyclerView base) {
        view = base;
        view.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        view.setLayoutManager(layoutManager);

        return view;
    }
}
