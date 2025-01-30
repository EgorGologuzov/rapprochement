package com.nti.rapprochement.adapters;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.utils.Event;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class RecordAdapter extends RecyclerView.Adapter<RecordBase.RecordHolder> {

    final private List<RecordBase> records;

    public final Event<Integer> onItemInserted = new Event<>();
    public final Event<Integer> onItemRemoved = new Event<>();

    public RecordAdapter(List<RecordBase> records) {
        this.records = records;
    }

    @NotNull
    @Override
    public RecordBase.RecordHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        RecordBase model = records.get(position);
        return model.getHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecordBase.RecordHolder holder, int position) {
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
        int index = records.size() - 1;
        item.onUpdate.add(this::onUpdateItem);
        notifyItemInserted(index);
        onItemInserted.call(index);
    }

    public void removeItem(RecordBase item) {
        int index = records.indexOf(item);
        records.remove(index);
        item.onUpdate.remove(this::onUpdateItem);
        notifyItemRemoved(index);
        onItemRemoved.call(index);
    }

    public void onUpdateItem(RecordBase item) {
        int index = records.indexOf(item);
        notifyItemChanged(index);
    }
}
