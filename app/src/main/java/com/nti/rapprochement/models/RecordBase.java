package com.nti.rapprochement.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;

public abstract class RecordBase {

    public abstract RecordVMBase getView(ViewGroup parent);

    public View getViewByResourceId(int id, ViewGroup parent) {
        return LayoutInflater
                .from(parent.getContext())
                .inflate(id, parent, false);
    }

    public static abstract class RecordVMBase extends RecyclerView.ViewHolder {
        final protected RecordBase model;

        public RecordVMBase(View itemView, RecordBase model) {
            super(itemView);
            this.model = model;
        }

        public abstract void bind();
    }
}
