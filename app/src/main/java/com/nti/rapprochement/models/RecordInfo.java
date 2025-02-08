package com.nti.rapprochement.models;

import com.nti.rapprochement.viewmodels.RecordBaseVM;
import com.nti.rapprochement.viewmodels.RecordInfoVM;

public class RecordInfo extends RecordBase {
    @Override
    public RecordBaseVM createViewModel() {
        return new RecordInfoVM();
    }
}
