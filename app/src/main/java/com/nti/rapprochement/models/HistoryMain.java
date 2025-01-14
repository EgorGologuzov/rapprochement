package com.nti.rapprochement.models;

public class HistoryMain extends HistoryBase {
    public static final HistoryMain shared = new HistoryMain();

    public HistoryMain() {
        RecordInfo info = new RecordInfo();
        this.push(info);
    }
}
