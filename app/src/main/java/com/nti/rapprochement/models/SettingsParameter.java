package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.components.SettingsParameterVF;
import com.nti.rapprochement.components.ViewFactoryBase;
import com.nti.rapprochement.utils.Event;

import java.util.function.Consumer;

public class SettingsParameter extends ModelBase {
    private String name;
    private String value;

    public final Event<SettingsParameter> onClick = new Event<>();
    public final Event<SettingsParameter> onValueChange = new Event<>();

    public SettingsParameter(String name, String value, Consumer<SettingsParameter> onClick) {
        this.name = name;
        this.value = value;
        this.onClick.add(onClick);
        setViewFactory(new SettingsParameterVF(this));
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        onValueChange.call(this);
    }

    public void callOnClick() {
        onClick.call(this);
    }
}
