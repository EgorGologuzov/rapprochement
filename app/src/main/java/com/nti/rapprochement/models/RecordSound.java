package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordSound extends RecordBase {

    @Override
    public RecordVMBase getViewModel(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.record_sound, parent);
        return new RecordSoundVM(view);
    }

    public static class RecordSoundVM extends RecordVMBase {
        final private TextView textView;

        public RecordSoundVM(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind(RecordBase model) {
            textView.setText("[Запись из Звука: Не реализованно]");
        }
    }
}
