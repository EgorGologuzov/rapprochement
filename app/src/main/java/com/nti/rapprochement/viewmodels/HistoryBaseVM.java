package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.utils.Event;
import com.nti.rapprochement.views.HistoryBaseView;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class HistoryBaseVM {

    private final HistoryBase model;
    private final ArrayList<RecordBaseVM> viewModels;
    private final Adapter adapter;

    private Consumer<Integer> itemInsertedListener;

    public HistoryBaseVM(HistoryBase model) {
        this.model = model;
        this.viewModels = new ArrayList<>();
        this.adapter = new Adapter(viewModels);

        model.records.forEach(record -> viewModels.add(record.createViewModel()));
    }

    public View createView(ViewGroup parent) {
        return HistoryBaseView.create(parent, this);
    }

    public void add(RecordBase record) {
        int index = model.records.size();
        model.records.add(record);
        viewModels.add(record.createViewModel());
        adapter.notifyItemInserted(index);
        if (itemInsertedListener != null) {
            itemInsertedListener.accept(index);
        }
    }

    public void remove(RecordBase record) {
        int index = model.records.indexOf(record);
        if (index >= 0) {
            model.records.remove(index);
            viewModels.remove(index).dispose();
            adapter.notifyItemRemoved(index);
        }
    }

    public void update(RecordBase record) {
        int index = model.records.indexOf(record);
        if (index >= 0) {
            adapter.notifyItemChanged(index);
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setItemInsertedListener(Consumer<Integer> itemInsertedListener) {
        this.itemInsertedListener = itemInsertedListener;
    }

    public RecordBaseVM findViewModel(RecordBase record) {
        int index = model.records.indexOf(record);
        return index >= 0 ? viewModels.get(index) : null;
    }

    public RecordBaseVM findViewModel(Function<RecordBaseVM, Boolean> selector) {
        for (RecordBaseVM vm : viewModels) {
            if (selector.apply(vm)) {
                return vm;
            }
        }
        return null;
    }

    public static class Adapter extends RecyclerView.Adapter<RecordBaseVM.RecordHolder> {

        private final ArrayList<RecordBaseVM> viewModels;

        public Adapter(ArrayList<RecordBaseVM> viewModels) {
            this.viewModels = viewModels;
        }

        @NonNull
        @Override
        public RecordBaseVM.RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            RecordBaseVM vm = viewModels.get(position);
            return vm.createHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecordBaseVM.RecordHolder holder, int position) {
            RecordBaseVM vm = viewModels.get(position);
            holder.bind(vm);
        }

        @Override
        public int getItemCount() {
            return viewModels.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}
