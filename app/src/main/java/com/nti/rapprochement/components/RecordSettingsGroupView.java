package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.RecordSettingsGroup;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.ViewsUtils;

public class RecordSettingsGroupView {
    public static View create(ViewGroup parent, RecordSettingsGroup model) {
        LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.record_settings_group, parent);

        TextView titleView = view.findViewById(R.id.groupTitle);

        titleView.setText(model.getTitle());

        for (SettingsParameter param : model.getParameters()) {
            View paramView = param.getView(view);
            view.addView(paramView);
        }

        return view;
    }
}
