package com.nti.rapprochement.components;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class RModeInputTextVF extends ViewFactoryBase {
    private static final int MAX_LENGTH = 500;

    private final RecordMultiMode model;

    public RModeInputTextVF(RecordMultiMode model) {
        this.model = model;
    }

//    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.rmode_input_text, parent);
        EditText edit = view.findViewById(R.id.editText);
        TextView counter = view.findViewById(R.id.symbolCount);

        edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_LENGTH) });
        counter.setText(formatCounterString(0));

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counter.setText(formatCounterString(s.length()));
                model.setText(s.toString());
                if (s.length() == MAX_LENGTH) App.showToast(Res.str(R.string.toast_is_max_text_length));
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        edit.setText(model.getText());

        edit.requestFocus();
        App.openKeyboard();

        return view;
    }

    @SuppressLint("DefaultLocale")
    private static String formatCounterString(int count) {
        return String.format("%d/%d", count, MAX_LENGTH);
    }
}
