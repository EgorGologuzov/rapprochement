package com.nti.rapprochement.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Settings;

import java.util.ArrayList;

public class RecordSettingsGroup extends RecordBase {
    public final int titleId;
    public final ArrayList<SettingsParameter> parameters;

    public RecordSettingsGroup(int titleId, ArrayList<SettingsParameter> parameters) {
        this.titleId = titleId;
        this.parameters = parameters;
    }

    public static RecordSettingsGroup initGeneralGroup() {
        ArrayList<SettingsParameter> params = new ArrayList<>();

        params.add(new SettingsParameter(
                R.string.theme,
                Settings.getTheme() ? R.string.theme_light : R.string.theme_dark,
                (sp) -> {
                    boolean isDarkMode = Settings.getTheme();
                    Settings.setTheme(!isDarkMode);
                    sp.setValueId(isDarkMode ? R.string.theme_dark : R.string.theme_light);
                }));

        params.add(new SettingsParameter(
                R.string.font,
                Settings.fontSizeToStringId(Settings.getFontSize()),
                (sp) -> {
                    Resources res = sp.getRes();
                    Context context = sp.getViewContext();
                    String[] variants = Settings.getFontSizeVariants(res);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder
                        .setTitle(sp.getRes().getString(R.string.font))
                        .setNegativeButton(sp.getRes().getString(R.string.btn_cancel), null)
                        .setItems(variants, (dialog, which) -> {
                            Settings.FontSize fs = Settings.stringVariantToFontSize(variants[which], res);
                            Settings.setFontSize(fs);
                            sp.setValueId(Settings.fontSizeToStringId(fs));
                        });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }));

        return new RecordSettingsGroup(R.string.title_general, params);
    }

    @Override
    public RecordVMBase getVM(ViewGroup parent) {
        View view = getViewByResourceId(R.layout.record_settings_group, parent);
        return new RecordSettingsGroupVM(view, this);
    }

    public static class RecordSettingsGroupVM extends RecordVMBase {
        final private LinearLayout parametersGroupView;
        final private TextView titleView;
        final private Resources res;

        public RecordSettingsGroupVM(View itemView, RecordBase model) {
            super(itemView, model);
            parametersGroupView = (LinearLayout) itemView;
            titleView = itemView.findViewById(R.id.groupTitle);
            res = itemView.getResources();
        }

        @Override
        public void bind() {
            RecordSettingsGroup model = (RecordSettingsGroup) this.model;

            titleView.setText(res.getString(model.titleId));

            for (SettingsParameter param : model.parameters) {
                View view = param.getView(this.parametersGroupView);
                parametersGroupView.addView(view);
            }
        }
    }
}
