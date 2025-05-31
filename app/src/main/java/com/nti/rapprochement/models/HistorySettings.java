package com.nti.rapprochement.models;

import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.utils.Convert;
import com.nti.rapprochement.viewmodels.HistoryBaseVM;
import com.nti.rapprochement.viewmodels.HistorySettingsVM;
import com.nti.rapprochement.views.DialogSelectVariant;

import java.util.ArrayList;

public class HistorySettings extends HistoryBase {

    public HistorySettings() {
        records.add(initGeneralGroup());
    }

    @Override
    public HistoryBaseVM createViewModel() {
        return new HistorySettingsVM(this);
    }

    public static RecordSettingsGroup initGeneralGroup() {
        ArrayList<SettingsParameter> params = new ArrayList<>();

        params.add(new SettingsParameter(
                Res.str(R.string.theme),
                Convert.darkModeToString(Settings.getDarkMode()),
                (vm) -> {
                    boolean isDarkMode = Settings.getDarkMode();
                    Settings.setDarkMode(!isDarkMode);
                    vm.setValue(Convert.darkModeToString(!isDarkMode));
                }
        ));

        params.add(new SettingsParameter(
                Res.str(R.string.font),
                Convert.fontSizeToString(Settings.getFontSize()),
                (vm) -> {
                    String[] variants = Convert.getFontSizeStringVariants();
                    DialogSelectVariant.show(
                            Res.str(R.string.font),
                            variants,
                            (selectedIndex) -> {
                                String resultStr = variants[selectedIndex];
                                Settings.FontSize result = Convert.stringToFontSize(resultStr);
                                Settings.setFontSize(result);
                                vm.setValue(resultStr);
                            },
                            null
                    );
                }
        ));

        return new RecordSettingsGroup(Res.str(R.string.title_general), params);
    }
}
