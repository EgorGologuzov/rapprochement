package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;

public class RecordGesture extends RecordBase {

    @Override
    public RecordBase.RecordVMBase getView(ViewGroup parent) {
        View view = getViewByResourceId(R.layout.record_gesture, parent);
        return new RecordGestureVM(view, this);
    }

    public static class RecordGestureVM extends RecordBase.RecordVMBase {
        final private TextView textView;

        public RecordGestureVM(View itemView, RecordBase model) {
            super(itemView, model);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind() {
            textView.setText("[Запись из Жестов: Не реализованно]");
        }
    }
}
