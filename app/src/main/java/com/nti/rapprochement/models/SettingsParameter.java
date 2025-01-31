package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.components.SettingsParameterVF;
import com.nti.rapprochement.components.ViewFactoryBase;
import com.nti.rapprochement.utils.Event;

import java.util.function.Consumer;

public class SettingsParameter {
    private final ViewFactoryBase viewFactory = new SettingsParameterVF(this);

    private String name;
    private String value;

    public final Event<SettingsParameter> onClick = new Event<>();
    public final Event<String> onValueChange = new Event<>();

    public SettingsParameter(String name, String value, Consumer<SettingsParameter> onClick) {
        this.name = name;
        this.value = value;
        this.onClick.add(onClick);
    }

    public View createView(ViewGroup parent) {
        return viewFactory.create(parent);
    }

    public void destroyView(View view) {
        viewFactory.destroy(view);
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
