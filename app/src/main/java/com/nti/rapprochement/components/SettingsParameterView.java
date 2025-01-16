package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.function.Consumer;

public class SettingsParameterView {
    public static View create(ViewGroup parent, SettingsParameter param) {
        View view = ViewsUtils.createView(R.layout.settings_group_item, parent);

        TextView nameView = view.findViewById(R.id.parameterName);
        TextView valueView = view.findViewById(R.id.parameterValue);

        nameView.setText(param.getName());
        valueView.setText(param.getValue());
        valueView.setOnClickListener(v -> {
            Consumer<SettingsParameter> onClick = param.getOnClick();
            if (onClick != null) onClick.accept(param);
        });

        param.onValueChange.add(valueView::setText);

        return view;
    }
}
