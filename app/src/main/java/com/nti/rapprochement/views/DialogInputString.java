package com.nti.rapprochement.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.function.Consumer;

public class DialogInputString {
    public static void show(String title, Consumer<String> onOk, Runnable onCancel) {

        final LinearLayout view = (LinearLayout) ViewsUtils.createView(R.layout.dialog_input_string, App.current.getDialogContext());
        final EditText input = view.findViewById(R.id.stringEdit);

        AlertDialog.Builder builder = new AlertDialog.Builder(App.current.getDialogContext());
        builder
                .setTitle(title)
                .setView(view)
                .setPositiveButton(R.string.btn_ok, (di, i) -> {
                    if (onOk != null) {
                        onOk.accept(input.getText().toString());
                    }
                })
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.cancel())
                .setOnCancelListener(di -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
