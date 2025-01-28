package com.nti.rapprochement.components;

import android.text.TextUtils;
import android.view.View;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.RModeShowText;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

public class PanelInputTextView {
    public static View create(RecordMultiMode model) {
        View view = ViewsUtils.createView(R.layout.panel_input_text, App.getPanelContext());

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    model.deactivatePanel();
                    HistoryMain.shared.remove(model);
                    App.hideKeyboard();
                });

        view.findViewById(R.id.toText)
                .setOnClickListener(v -> {
                    if (isInputTextEmpty(model.getText())) return;

                    RModeShowText mode = new RModeShowText();
                    model.setMode(mode);
                    model.activatePanel();
                    model.update();
                    App.hideKeyboard();
                });

        return view;
    }

    private static boolean isInputTextEmpty(String text) {
        if (TextUtils.isEmpty(text)) {
            App.showToast(Res.str(R.string.toast_input_text_is_empty));
            return true;
        }

        return false;
    }
}
