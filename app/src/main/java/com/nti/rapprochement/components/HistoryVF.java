package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.RecordAdapter;

import java.util.function.Consumer;

public class HistoryVF extends ViewFactoryBase {
    private final RecordAdapter adapter;

    public HistoryVF(RecordAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View create(ViewGroup parent) {
        OptionalData optionalData = new OptionalData();
        RecyclerView view = (RecyclerView) createAndRegister(R.layout.history, parent, optionalData);

        view.setAdapter(adapter);
        optionalData.adapterOnItemInsertedHandler = index -> view.smoothScrollToPosition(index);
        adapter.onItemInserted.add(optionalData.adapterOnItemInsertedHandler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);
        view.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void destroy(View view) {
        super.destroy(view);
        if (currentOptionalData != null) {
            OptionalData od = (OptionalData) currentOptionalData;
            adapter.onItemInserted.remove(od.adapterOnItemInsertedHandler);
        }
    }

    private static class OptionalData {
        public Consumer<Integer> adapterOnItemInsertedHandler;
    }
}
