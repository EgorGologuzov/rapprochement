package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.SettingsParameterVM;

import java.util.function.Consumer;

public class SettingsParameter {
    public String name;
    public String value;
    public Consumer<SettingsParameterVM> action;

    public SettingsParameter(String name, String value, Consumer<SettingsParameterVM> action) {
        this.name = name;
        this.value = value;
        this.action = action;
    }

    public SettingsParameterVM createViewModel() {
        return new SettingsParameterVM(this);
    }
}
