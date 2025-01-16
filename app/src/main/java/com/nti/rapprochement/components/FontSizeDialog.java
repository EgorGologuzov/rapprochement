package com.nti.rapprochement.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;

import java.util.function.Consumer;

public class FontSizeDialog {
    public static void show(Consumer<Settings.FontSize> onVariantSelected) {
        String[] variants = Settings.getFontSizeVariants();

        AlertDialog.Builder builder = new AlertDialog.Builder(App.getHistoryContext());
        builder
            .setTitle(Res.str(R.string.font))
            .setNegativeButton(Res.str(R.string.btn_cancel), null)
            .setItems(variants, (dialog, which) -> {
                Settings.FontSize fs = Settings.stringVariantToFontSize(variants[which]);
                if (onVariantSelected != null) onVariantSelected.accept(fs);
            });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
