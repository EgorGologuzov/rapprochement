package com.nti.rapprochement.models;

import android.view.View;

import com.nti.rapprochement.viewmodels.PanelBaseVM;
import com.nti.rapprochement.viewmodels.PanelCallVM;
import com.nti.rapprochement.viewmodels.RecordCallVM;

import java.util.function.Function;

public class PanelCall extends PanelBase {

    public final RecordCall recordModel;
    public final Function<RecordCallVM.CreateArgs, View> viewCreator;

    public PanelCall(RecordCall recordModel, Function<RecordCallVM.CreateArgs, View> panelCreator) {
        this.recordModel = recordModel;
        this.viewCreator = panelCreator;
    }

    @Override
    public PanelBaseVM createViewModel() {
        return new PanelCallVM(this);
    }
}
