package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.utils.ViewsUtils;

public class HistoryVF extends ViewFactoryBase {
    private final HistoryBase model;

    public HistoryVF(HistoryBase model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        RecyclerView view = (RecyclerView) createAndRegister(R.layout.history, parent);

        view.setAdapter(model.getAdapter());
        model.getAdapter().onItemInserted.add(view::smoothScrollToPosition);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);
        view.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void destroy(View view) {
        super.destroy(view);
        RecyclerView rv = (RecyclerView) view;
        model.getAdapter().onItemRemoved.remove(rv::smoothScrollToPosition);
    }
}
