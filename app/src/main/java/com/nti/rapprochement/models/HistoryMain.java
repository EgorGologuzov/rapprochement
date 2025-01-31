package com.nti.rapprochement.models;

public class HistoryMain extends HistoryBase {
    public static final HistoryMain current = new HistoryMain();

    private static RecordMultiMode focusedRecord;

    public HistoryMain() {
        RecordInfo info = new RecordInfo();
        this.add(info);
    }

    public void requestFocus(RecordMultiMode record) {
        if (focusedRecord != null && focusedRecord != record) {
            RModeShowText defaultMode = new RModeShowText(focusedRecord);
            focusedRecord.setMode(defaultMode);
            focusedRecord.update();
        }
        focusedRecord = record;
    }

    public void removeFocus(RecordMultiMode record) {
        if (focusedRecord == record) {
            focusedRecord = null;
        }
    }

    @Override
    public void remove(RecordBase record) {
        super.remove(record);
        focusedRecord = focusedRecord == record ? null : focusedRecord;
    }
}
