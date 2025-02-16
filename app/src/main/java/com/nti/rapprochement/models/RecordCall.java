package com.nti.rapprochement.models;

import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.viewmodels.RecordBaseVM;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.Date;

public class RecordCall extends RecordBase {

    public enum SourceType { Gesture, Sound, Text }

    public int id;
    public SourceType sourceType;
    public Date creationTime;
    public String text;

    private RecordCall() {}

    @Override
    public RecordBaseVM createViewModel() {
        return new RecordCallVM(this);
    }

    public static RecordCall createDefault(SourceType sourceType) {
        RecordCall record = new RecordCall();
        record.id = Settings.getNextId();
        record.sourceType = sourceType;
        record.creationTime = new Date();
        return record;
    }

    public static RecordCall createFromData(int id, SourceType sourceType, Date creationTime, String text) {
        RecordCall record = new RecordCall();
        record.id = id;
        record.sourceType = sourceType;
        record.creationTime = creationTime;
        record.text = text;
        return record;
    }
}
