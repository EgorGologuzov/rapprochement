package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordSettingsGroupVM;

public class RecordSettingsGroupView {
    public static View create(ViewGroup parent, RecordSettingsGroupVM vm) {
        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.record_settings_group, parent);
        TextView titleView = view.findViewById(R.id.groupTitle);
        LinearLayout parametersList = view.findViewById(R.id.parametersList);

        titleView.setText(vm.getTitle());

        for (SettingsParameter param : vm.getParameters()) {
            View paramView = param.createViewModel().createView(parametersList);
            parametersList.addView(paramView);
        }

        return view;
    }
}
