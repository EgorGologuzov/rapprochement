package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.RecordSettingsGroup;
import com.nti.rapprochement.models.SettingsParameter;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.ArrayList;

public class RecordSettingsGroupVF extends ViewFactoryBase {
    private final RecordSettingsGroup model;

    public RecordSettingsGroupVF(RecordSettingsGroup model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        LinearLayout view = (LinearLayout) createAndRegister(R.layout.record_settings_group, parent);

        LinearLayout parametersList = view.findViewById(R.id.parametersList);
        TextView titleView = view.findViewById(R.id.groupTitle);

        titleView.setText(model.getTitle());

        for (SettingsParameter param : model.getParameters()) {
            View paramView = param.createView(view);
            parametersList.addView(paramView);
        }

        return view;
    }

    @Override
    public void destroy(View view) {
        super.destroy(view);

        LinearLayout ll = view.findViewById(R.id.parametersList);
        ArrayList<SettingsParameter> params = model.getParameters();

        for (int i = 0; i < params.size(); i++) {
            View paramView = ll.getChildAt(i);
            SettingsParameter param = params.get(i);
            param.destroyView(paramView);
        }
    }
}
