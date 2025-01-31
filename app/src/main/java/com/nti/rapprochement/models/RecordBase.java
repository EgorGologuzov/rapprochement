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
    private ViewFactoryBase viewFactory = new TextViewVF(R.string.record_view_not_set);

    public Event<RecordBase> onUpdate = new Event<>();

    public RecordHolder createHolder(ViewGroup parent) {
        return new RecordHolder(createView(parent));
    }

    public void bind(View view) {}

    public void setViewFactory(ViewFactoryBase viewFactory) {
        this.viewFactory = viewFactory;
    }

    public View createView(ViewGroup parent) {
        return viewFactory.create(parent);
    }

    public void destroyView(View view) {
        viewFactory.destroy(view);
    }

    public static class RecordHolder extends RecyclerView.ViewHolder {
        private RecordBase model;
        public RecordHolder(@NonNull View itemView) { super(itemView); }
        public void bind(RecordBase model) { this.model = model; model.bind(itemView); }
        public void destroy() { model.destroyView(itemView); }
    }
}
