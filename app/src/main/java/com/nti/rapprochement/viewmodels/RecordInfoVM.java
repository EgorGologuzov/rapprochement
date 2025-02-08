package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.views.RecordInfoView;

public class RecordInfoVM extends RecordBaseVM {
    @Override
    public View createView(ViewGroup parent) {
        return RecordInfoView.create(parent);
    }
}
