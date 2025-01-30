package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.TextViewVF;
import com.nti.rapprochement.components.ViewFactoryBase;
import com.nti.rapprochement.utils.Event;

public abstract class RecordBase {

    public Event<RecordBase> onUpdate = new Event<>();

    public RecordHolder getHolder(ViewGroup parent) {
        return new RecordHolder(getViewFactory().create(parent));
    }

    public ViewFactoryBase getViewFactory() {
        return new TextViewVF(R.string.record_view_not_set);
    }

    public void bind(View view) {}

    public static class RecordHolder extends RecyclerView.ViewHolder {
        public RecordHolder(@NonNull View itemView) { super(itemView); }
        public void bind(RecordBase model) { model.bind(itemView); }
    }
}
