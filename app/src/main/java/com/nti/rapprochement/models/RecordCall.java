package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.RecordBaseVM;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.Date;

public class RecordCall extends RecordBase {

    public enum SourceType { Gesture, Sound, Text }

    public final SourceType sourceType;
    public String text;
    public Date creationTime;


    public RecordCall(SourceType sourceType) {
        this.sourceType = sourceType;
        this.creationTime = new Date();
    }

    @Override
    public RecordBaseVM createViewModel() {
        return new RecordCallVM(this);
    }
}
