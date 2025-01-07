package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;

public class RecordText extends RecordBase {

    @Override
    public RecordVMBase getView(ViewGroup parent) {
        View view = getViewByResourceId(R.layout.record_text, parent);
        return new RecordTextVM(view, this);
    }

    public static class RecordTextVM extends RecordVMBase {
        final private TextView textView;

        public RecordTextVM(View itemView, RecordBase model) {
            super(itemView, model);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind() {
            textView.setText("[Запись из Текста: Не реализованно]");
        }
    }
}
