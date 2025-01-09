package com.nti.rapprochement.adapters;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.models.RecordBase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordBase.RecordVMBase> {

    final private List<RecordBase> records;

    public RecordAdapter(List<RecordBase> records) {
        this.records = records;
    }

    @NotNull
    @Override
    public RecordBase.RecordVMBase onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        RecordBase model = records.get(position);
        return model.getVM(parent);
    }

    @Override
    public void onBindViewHolder(RecordBase.RecordVMBase holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
