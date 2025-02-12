package com.nti.rapprochement.views;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.StringRes;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.utils.Convert;

import java.util.function.Consumer;

public class MessageDialog {
    public static void show(@StringRes int message, Runnable onClose) {

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
