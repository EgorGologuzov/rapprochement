package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordGesture extends RecordBase {

    @Override
    public RecordBase.RecordVMBase getViewModel(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.record_gesture, parent);
        return new RecordGestureVM(view);
    }

    public static class RecordGestureVM extends RecordBase.RecordVMBase {
        final private TextView textView;

        public RecordGestureVM(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind(RecordBase model) {
            textView.setText("[Запись из Жестов: Не реализованно]");
        }
    }
}
