package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.RecordInfoView;

public class RecordInfo extends RecordBase {
    @Override
    public View getView(ViewGroup parent) {
        return RecordInfoView.create(parent);
    }
}
