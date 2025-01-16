package com.nti.rapprochement.models;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.SettingsParameterView;
import com.nti.rapprochement.utils.Event;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.function.Consumer;

public class SettingsParameter {
    private String name;
    private String value;
    private Consumer<SettingsParameter> onClick;

    public final Event<String> onValueChange = new Event<>();

    public SettingsParameter(String name, String value, Consumer<SettingsParameter> onClick) {
        this.name = name;
        this.value = value;
        this.onClick = onClick;
    }

    public View getView(ViewGroup parent) {
        return SettingsParameterView.create(parent, this);
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

    public Consumer<SettingsParameter> getOnClick() {
        return onClick;
    }
}
