package com.nti.rapprochement.views;

import android.app.AlertDialog;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.utils.Convert;

import java.util.function.Consumer;

public class DialogSelectVariant {
    public static void show(String title, String[] variants, Consumer<Integer> onSelect, Runnable onCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(App.current.getDialogContext());
        builder
                .setTitle(title)
                .setItems(variants, (dialog, which) -> {
                    if (onSelect != null) {
                        onSelect.accept(which);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, (di, i) -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
