package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class RModeInputTextView {
    public static View create(ViewGroup parent, RecordMultiMode model) {
        View view = ViewsUtils.createView(R.layout.rmode_input_text, parent);
        view.findViewById(R.id.editText).requestFocus();
        App.setKeyboardOpen(true);
        return view;
    }
}
