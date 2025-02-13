package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.Event;
import com.nti.rapprochement.views.SettingsParameterView;

import java.util.function.Consumer;

public class SettingsParameterVM {

    private final SettingsParameter model;

    private Consumer<SettingsParameterVM> valueChangeListener;

    public SettingsParameterVM(SettingsParameter model) {
        this.model = model;
    }

    public View createView(ViewGroup parent) {
        return SettingsParameterView.create(parent, this);
    }

    public String getName() {
        return model.name;
    }

    public String getValue() {
        return model.value;
    }

    public void setValue(String value) {
        model.value = value;
        if (valueChangeListener != null) {
            valueChangeListener.accept(this);
        }
    }

    public void setValueChangeListener(Consumer<SettingsParameterVM> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void callAction() {
        model.action.accept(this);
    }
}
