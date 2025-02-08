package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.RecordBaseVM;
import com.nti.rapprochement.viewmodels.RecordSettingsGroupVM;

import java.util.ArrayList;

public class RecordSettingsGroup extends RecordBase {
    public String title;
    public ArrayList<SettingsParameter> parameters;

    public RecordSettingsGroup(String title, ArrayList<SettingsParameter> parameters) {
        this.title = title;
        this.parameters = parameters;
    }

    @Override
    public RecordBaseVM createViewModel() {
        return new RecordSettingsGroupVM(this);
    }
}
