package com.nti.rapprochement.models;

public class HistorySettings extends HistoryBase {
    public static final HistorySettings shared = new HistorySettings();

    public HistorySettings() {
        RecordSettingsGroup settingsGroup1 = RecordSettingsGroup.initGeneralGroup();
        this.push(settingsGroup1);
    }
}
