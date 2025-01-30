package com.nti.rapprochement.models;

public class HistoryMain extends HistoryBase {
    public static final HistoryMain shared = new HistoryMain();

    private static RecordMultiMode focusedRecord;

    public HistoryMain() {
        RecordInfo info = new RecordInfo();
        this.add(info);
    }

    public void setFocus(RecordMultiMode record) {
        if (focusedRecord != null) {
            RModeShowText defaultMode = new RModeShowText(focusedRecord);
            focusedRecord.setMode(defaultMode);
            focusedRecord.update();
        }

        focusedRecord = record;
    }

    @Override
    public void remove(RecordBase record) {
        super.remove(record);
        focusedRecord = focusedRecord == record ? null : focusedRecord;
    }
}
