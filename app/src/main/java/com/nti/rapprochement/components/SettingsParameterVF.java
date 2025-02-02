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
        OptionalData optionalData = new OptionalData();
        View view = createAndRegister(R.layout.settings_parameter, parent, optionalData);

        TextView nameView = view.findViewById(R.id.parameterName);
        TextView valueView = view.findViewById(R.id.parameterValue);

        nameView.setText(param.getName());
        valueView.setText(param.getValue());
        valueView.setOnClickListener(v -> param.callOnClick());

        optionalData.paramOnValueChangeHandler = sp -> valueView.setText(sp.getValue());
        param.onValueChange.add(optionalData.paramOnValueChangeHandler);

        return view;
    }

    @Override
    public void destroy(View view) {
        super.destroy(view);
        if (currentOptionalData != null) {
            OptionalData od = (OptionalData) currentOptionalData;
            param.onValueChange.remove(od.paramOnValueChangeHandler);
        }
    }

    private static class OptionalData {
        public Consumer<SettingsParameter> paramOnValueChangeHandler;
    }
}
