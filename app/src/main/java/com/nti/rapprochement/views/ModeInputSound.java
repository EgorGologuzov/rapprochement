package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

public class ModeInputSound extends RecordCallVM.Mode {
    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;
        View view = ViewsUtils.createView(R.layout.mode_input_sound, parent);
        return view;
    }

    @Override
    public View createPanelView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;
        View view = ViewsUtils.createView(R.layout.panel_input_sound, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    vm.deactivatePanel();
                    vm.removeSelfFromHistory();
                });

        return view;
    }

    @Override
    public boolean hasPanel() {
        return true;
    }
}
