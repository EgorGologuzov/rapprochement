package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.SettingsParameterVM;

public class SettingsParameterView {
    public static View create(ViewGroup parent, SettingsParameterVM vm) {
        View view = ViewsUtils.createView(R.layout.settings_parameter, parent);
        TextView nameView = view.findViewById(R.id.parameterName);
        TextView valueView = view.findViewById(R.id.parameterValue);

        nameView.setText(vm.getName());
        valueView.setText(vm.getValue());
        valueView.setOnClickListener(v -> vm.callAction());

        vm.onValueChange.add(vm2 -> valueView.setText(vm2.getValue()));

        return view;
    }
}
