package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.RecordInfoView;

public class RecordInfo extends RecordBase {

    @Override
    public RecordVMBase getViewModel(ViewGroup parent) {
        View view = RecordInfoView.create(parent);
        return new RecordInfoVM(view);
    }

    public static class RecordInfoVM extends RecordVMBase {
        public RecordInfoVM(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(RecordBase model) {}
    }
}
