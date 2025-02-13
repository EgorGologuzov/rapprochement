package com.nti.rapprochement.views;

import android.app.AlertDialog;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.utils.Convert;

import java.util.function.Consumer;

public class DialogFontSize {
    public static void show(Consumer<Settings.FontSize> onVariantSelected) {
        String[] variants = Convert.getFontSizeStringVariants();

        AlertDialog.Builder builder = new AlertDialog.Builder(App.current.getDialogContext());
        builder
                .setTitle(R.string.font)
                .setNegativeButton(R.string.btn_cancel, null)
                .setItems(variants, (dialog, which) -> {
                    Settings.FontSize fs = Convert.stringToFontSize(variants[which]);
                    if (onVariantSelected != null) onVariantSelected.accept(fs);
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
