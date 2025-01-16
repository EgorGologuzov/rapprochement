package com.nti.rapprochement.adapters;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.models.RecordBase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class RecordAdapter extends RecyclerView.Adapter<RecordBase.RecordVMBase> {

    final private List<RecordBase> records;
    private Consumer<Integer> onItemInsertedListener;
    private Consumer<Integer> onItemRemovedListener;

    public RecordAdapter(List<RecordBase> records) {
        this.records = records;
    }

    @NotNull
    @Override
    public RecordBase.RecordVMBase onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        RecordBase model = records.get(position);
        return model.getViewModel(parent);
    }

    @Override
    public void onBindViewHolder(RecordBase.RecordVMBase holder, int position) {
        RecordBase model = records.get(position);
        holder.bind(model);
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
        records.add(item);
        notifyItemInserted(records.size() - 1);
        if (onItemInsertedListener != null) onItemInsertedListener.accept(records.size() - 1);
    }

    public void removeItem(RecordBase item) {
        int index = records.indexOf(item);
        records.remove(index);
        notifyItemRemoved(index);
        if (onItemRemovedListener != null) onItemRemovedListener.accept(index);
    }

    public void removeLastItem() {
        int index = records.size() - 1;
        if (index < 0) return;
        records.remove(index);
        notifyItemRemoved(index);
        if (onItemRemovedListener != null) onItemRemovedListener.accept(index);
    }

    public void setOnItemInsertedListener(Consumer<Integer> handler) { onItemInsertedListener = handler; }

    public void setOnItemRemovedListener(Consumer<Integer> handler) { onItemRemovedListener = handler; }
}
