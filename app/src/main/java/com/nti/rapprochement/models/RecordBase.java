package com.nti.rapprochement.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecordBase {

    public abstract RecordVMBase getViewModel(ViewGroup parent);

    public static abstract class RecordVMBase extends RecyclerView.ViewHolder {
        public RecordVMBase(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(RecordBase model);
    }
}
