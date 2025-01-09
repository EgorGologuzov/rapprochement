package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;

public class RecordInfo extends RecordBase {

    @Override
    public RecordVMBase getVM(ViewGroup parent) {
        View view = getViewByResourceId(R.layout.record_info, parent);
        return new RecordInfoVM(view, this);
    }

    public static class RecordInfoVM extends RecordVMBase {
        final private TextView textView;

        public RecordInfoVM(View itemView, RecordBase model) {
            super(itemView, model);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind() {
            textView.setText("[Запись Инфо: Не реализованно]");
        }
    }
}
