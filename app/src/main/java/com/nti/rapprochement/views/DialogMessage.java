package com.nti.rapprochement.views;

import android.app.AlertDialog;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;

public class DialogMessage {
    public static void show(String message, Runnable onClose) {

        AlertDialog.Builder builder = new AlertDialog.Builder(App.current.getDialogContext());
        builder
                .setTitle(R.string.title_message)
                .setMessage(message)
                .setPositiveButton(R.string.btn_ok, null)
                .setOnDismissListener(dialogInterface -> {
                    if (onClose != null) {
                        onClose.run();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
