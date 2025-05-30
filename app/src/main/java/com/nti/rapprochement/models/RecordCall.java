package com.nti.rapprochement.models;

import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.viewmodels.RecordBaseVM;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.Date;

public class RecordCall extends RecordBase {

    public enum Status { Gesture, Sound, Text, Success, Error, Other }

    public int id;
    public Status status;
    public Date creationTime;
    public String text;

    private RecordCall() {}

    @Override
    public RecordBaseVM createViewModel() {
        return new RecordCallVM(this);
    }

    public static RecordCall createDefault(Status status) {
        RecordCall record = new RecordCall();
        record.id = Settings.getNextId();
        record.status = status;
        record.creationTime = new Date();
        return record;
    }

    public static RecordCall createFromData(int id, Status status, Date creationTime, String text) {
        RecordCall record = new RecordCall();
        record.id = id;
        record.status = status;
        record.creationTime = creationTime;
        record.text = text;
        return record;
    }
}
