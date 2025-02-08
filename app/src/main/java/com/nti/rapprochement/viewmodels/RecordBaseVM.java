package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecordBaseVM {

    public abstract View createView(ViewGroup parent);

    public RecordHolder createHolder(ViewGroup parent) {
        return new RecordHolder(createView(parent));
    }

    public void bind(View view) {}

    public static class RecordHolder extends RecyclerView.ViewHolder {
        public RecordHolder(@NonNull View itemView) { super(itemView); }
        public void bind(RecordBaseVM model) { model.bind(itemView); }
    }
}
