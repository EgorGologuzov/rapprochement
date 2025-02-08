package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.models.RecordSettingsGroup;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.views.RecordSettingsGroupView;

import java.util.ArrayList;

public class RecordSettingsGroupVM extends RecordBaseVM {

    private RecordSettingsGroup model;

    public RecordSettingsGroupVM(RecordSettingsGroup model) {
        this.model = model;
    }

    public String getTitle() {
        return model.title;
    }

    public ArrayList<SettingsParameter> getParameters() {
        return model.parameters;
    }

    @Override
    public View createView(ViewGroup parent) {
        return RecordSettingsGroupView.create(parent, this);
    }
}
