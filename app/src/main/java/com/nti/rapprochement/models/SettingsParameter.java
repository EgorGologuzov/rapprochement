package com.nti.rapprochement.models;

import com.nti.rapprochement.components.SettingsParameterVF;
import com.nti.rapprochement.components.ViewFactoryBase;
import com.nti.rapprochement.utils.Event;

import java.util.function.Consumer;

public class SettingsParameter {
    private String name;
    private String value;

    public final Event<SettingsParameter> onClick = new Event<>();
    public final Event<String> onValueChange = new Event<>();

    public SettingsParameter(String name, String value, Consumer<SettingsParameter> onClick) {
        this.name = name;
        this.value = value;
        this.onClick.add(onClick);
    }

    public ViewFactoryBase getViewFactory() {
        return new SettingsParameterVF(this);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        onValueChange.call(value);
    }

    public void callOnClick() {
        onClick.call(this);
    }
}
