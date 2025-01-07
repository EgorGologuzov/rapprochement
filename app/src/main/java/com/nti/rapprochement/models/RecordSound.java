package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;

public class RecordSound extends RecordBase {

    @Override
    public RecordVMBase getView(ViewGroup parent) {
        View view = getViewByResourceId(R.layout.record_sound, parent);
        return new RecordSoundVM(view, this);
    }

    public static class RecordSoundVM extends RecordVMBase {
        final private TextView textView;

        public RecordSoundVM(View itemView, RecordBase model) {
            super(itemView, model);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind() {
            textView.setText("[Запись из Звука: Не реализованно]");
        }
    }
}
