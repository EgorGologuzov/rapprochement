package com.nti.rapprochement.utils;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.viewmodels.RecordBaseVM;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordAdapter extends RecyclerView.Adapter<RecordBaseVM.RecordHolder> {

    private final ArrayList<RecordBase> records;
    private final HashMap<RecordBase, RecordBaseVM> viewModels;

    public RecordAdapter(ArrayList<RecordBase> records) {
        this.records = records;
        this.viewModels = new HashMap<>();

        for (RecordBase record : records) {
            viewModels.put(record, record.createViewModel());
        }
    }

    @NonNull
    @Override
    public RecordBaseVM.RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        RecordBase model = records.get(position);
        return findOrCreateViewModel(model).createHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordBaseVM.RecordHolder holder, int position) {
        RecordBase model = records.get(position);
        holder.bind(findOrCreateViewModel(model));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addItem(RecordBase item) {
        int index = records.size();
        records.add(item);
        viewModels.put(item, item.createViewModel());
        notifyItemInserted(index);
    }

    public void removeItem(RecordBase item) {
        int index = records.indexOf(item);
        if (index >= 0) {
            records.remove(index);
            viewModels.remove(item);
            notifyItemRemoved(index);
        }
    }

    public void notifyItemChanged(RecordBase item) {
        int index = records.indexOf(item);
        if (index >= 0) {
            notifyItemChanged(index);
        }
    }

    public RecordBaseVM findViewModel(RecordBase item) {
        return viewModels.get(item);
    }

    private RecordBaseVM findOrCreateViewModel(RecordBase item) {
        RecordBaseVM vm = viewModels.get(item);

        if (vm == null) {
            vm = item.createViewModel();
            viewModels.put(item, vm);
        }

        return vm;
    }
}
