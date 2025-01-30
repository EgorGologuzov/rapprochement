package com.nti.rapprochement.models;

import com.nti.rapprochement.components.RecordInfoVF;
import com.nti.rapprochement.components.ViewFactoryBase;

public class RecordInfo extends RecordBase {
    @Override
    public ViewFactoryBase getViewFactory() {
        return new RecordInfoVF();
    }
}
