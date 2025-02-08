package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.HistoryBaseVM;
import com.nti.rapprochement.viewmodels.HistoryMainVM;

public class HistoryMain extends HistoryBase {

    public HistoryMain() {
        records.add(new RecordInfo());
    }

    @Override
    public HistoryBaseVM createViewModel() {
        return new HistoryMainVM(this);
    }
}
