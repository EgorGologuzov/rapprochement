package com.nti.rapprochement.models;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.function.Consumer;

public class SettingsParameter {
    private int nameId;
    private int valueId;
    private Consumer<SettingsParameter> onClick;

    private View view;
    private Resources res;

    public SettingsParameter(int nameId, int valueId, Consumer<SettingsParameter> onClick) {
        this.nameId = nameId;
        this.valueId = valueId;
        this.onClick = onClick;
    }

    public View getView(ViewGroup parent) {
        view = ViewsUtils.inflateLayout(R.layout.settings_group_item, parent);
        res = parent.getResources();

        TextView name = view.findViewById(R.id.parameterName);
        TextView value = view.findViewById(R.id.parameterValue);

        name.setText(res.getString(this.nameId));
        value.setText(res.getString(this.valueId));
        value.setOnClickListener(v -> this.onClick.accept(this));

        return view;
    }

    public void setValueId(int valueId) {
        this.valueId = valueId;
        TextView valueView = view.findViewById(R.id.parameterValue);
        valueView.setText(res.getString(this.valueId));
    }

    public Context getViewContext() {
        return view.getContext();
    }

    public Resources getRes() {
        return res;
    }
}
