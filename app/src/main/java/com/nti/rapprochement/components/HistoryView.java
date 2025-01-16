package com.nti.rapprochement.components;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.utils.ViewsUtils;

public class HistoryView {
    public static RecyclerView create(RecordAdapter adapter) {
        RecyclerView view = (RecyclerView) ViewsUtils.createView(R.layout.history, App.getHistoryContext());

        view.setAdapter(adapter);
        adapter.setOnItemInsertedListener(view::smoothScrollToPosition);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);
        view.setLayoutManager(layoutManager);

        return view;
    }
}
