package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordText extends RecordBase {

    @Override
    public RecordVMBase getViewModel(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.record_text, parent);
        return new RecordTextVM(view);
    }

    public static class RecordTextVM extends RecordVMBase {
        final private TextView textView;

        public RecordTextVM(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind(RecordBase model) {
            textView.setText("[Запись из Текста: Не реализованно]");
        }
    }
}
