package com.nti.rapprochement.models;

import com.nti.rapprochement.R;
import com.nti.rapprochement.components.FontSizeDialog;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;

import java.util.ArrayList;

public class HistorySettings extends HistoryBase {
    public static final HistorySettings shared = new HistorySettings();

    public HistorySettings() {
        RecordSettingsGroup settingsGroup1 = initGeneralGroup();
        this.push(settingsGroup1);
    }

    public static RecordSettingsGroup initGeneralGroup() {
        ArrayList<SettingsParameter> params = new ArrayList<>();

        params.add(new SettingsParameter(
                Res.str(R.string.theme),
                Settings.themeToString(!Settings.getTheme()),
                (sp) -> {
                    boolean isDarkMode = Settings.getTheme();
                    Settings.setTheme(!isDarkMode);
                    sp.setValue(Settings.themeToString(isDarkMode));
                }));

        params.add(new SettingsParameter(
                Res.str(R.string.font),
                Settings.fontSizeToString(Settings.getFontSize()),
                (sp) -> FontSizeDialog.show(result -> {
                    Settings.setFontSize(result);
                    sp.setValue(Settings.fontSizeToString(result));
                })));

        return new RecordSettingsGroup(Res.str(R.string.title_general), params);
    }
}
