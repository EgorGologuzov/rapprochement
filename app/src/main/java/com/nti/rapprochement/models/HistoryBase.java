package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.HistoryBaseVM;

import java.util.ArrayList;

public abstract class HistoryBase {
    public final ArrayList<RecordBase> records = new ArrayList<>();
    public abstract HistoryBaseVM createViewModel();
}
