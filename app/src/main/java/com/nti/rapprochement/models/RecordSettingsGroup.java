package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;

public class RecordSettingsGroup extends RecordBase {

    @Override
    public RecordVMBase getVM(ViewGroup parent) {
        View view = getViewByResourceId(R.layout.record_settings_group, parent);
        return new RecordSettingsGroupVM(view, this);
    }

    public static class RecordSettingsGroupVM extends RecordVMBase {
        final private TextView textView;

        public RecordSettingsGroupVM(View itemView, RecordBase model) {
            super(itemView, model);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void bind() {
            textView.setText("[Группа настроек: Не реализованно]");
        }
    }
}
