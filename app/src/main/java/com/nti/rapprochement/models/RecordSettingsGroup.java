package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RecordSettingsGroupVF;
import com.nti.rapprochement.components.ViewFactoryBase;

import java.util.ArrayList;

public class RecordSettingsGroup extends RecordBase {
    private final String title;
    private final ArrayList<SettingsParameter> parameters;

    public RecordSettingsGroup(String title, ArrayList<SettingsParameter> parameters) {
        setViewFactory(new RecordSettingsGroupVF(this));
        this.title = title;
        this.parameters = parameters;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<SettingsParameter> getParameters() {
        return parameters;
    }
}
