package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.function.Consumer;

public class SettingsParameterVF extends ViewFactoryBase {
    private final SettingsParameter param;

    public SettingsParameterVF(SettingsParameter param) {
        this.param = param;
    }

    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.settings_parameter, parent);

        TextView nameView = view.findViewById(R.id.parameterName);
        TextView valueView = view.findViewById(R.id.parameterValue);

        nameView.setText(param.getName());
        valueView.setText(param.getValue());
        valueView.setOnClickListener(v -> param.callOnClick());

        param.onValueChange.add(valueView::setText);

        return view;
    }

    @Override
    public void destroy(View view) {
        TextView valueView = view.findViewById(R.id.parameterValue);
        param.onValueChange.remove(valueView::setText);
    }
}
