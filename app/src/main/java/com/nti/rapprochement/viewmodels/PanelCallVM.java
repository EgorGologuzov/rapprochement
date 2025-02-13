package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.App;
import com.nti.rapprochement.models.PanelCall;

public class PanelCallVM extends PanelBaseVM {

    private final PanelCall model;

    public PanelCallVM(PanelCall model) {
        this.model = model;
    }

    @Override
    public View createView(ViewGroup parent) {
        RecordCallVM vm = (RecordCallVM) App.current.getCurrentHistoryVM()
                .findViewModel(model.recordModel);

        return model.viewCreator.apply(new RecordCallVM.CreateArgs(parent, vm));
    }
}
