package com.nti.rapprochement.views;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistorySettings;
import com.nti.rapprochement.models.PanelSettings;
import com.nti.rapprochement.models.RecordCall;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordBaseVM;
import com.nti.rapprochement.viewmodels.RecordCallVM;

public class PanelMainView {
    public static View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.panel_main, parent);

        view.findViewById(R.id.settingsButton)
                .setOnClickListener(v -> {
                    App.current.navigate(new HistorySettings(), new PanelSettings());
                });

        view.findViewById(R.id.textButton)
                .setOnClickListener(v -> {
                    RecordCall record = new RecordCall(RecordCall.SourceType.Text);
                    addToCurrentHistory(record);
                    RecordCallVM vm = findViewModel(record);
                    vm.setMode(new ModeInputText());
                    vm.activatePanel();
                    vm.update();
                });

        view.findViewById(R.id.soundButton)
                .setOnClickListener(v -> {
                    RecordCall record = new RecordCall(RecordCall.SourceType.Sound);
                    addToCurrentHistory(record);
                    RecordCallVM vm = findViewModel(record);
                    vm.setMode(new ModeInputSound());
                    vm.activatePanel();
                    vm.update();
                });

        view.findViewById(R.id.gestureButton)
                .setOnClickListener(v -> {
                    RecordCall record = new RecordCall(RecordCall.SourceType.Gesture);
                    addToCurrentHistory(record);
                    RecordCallVM vm = findViewModel(record);
                    vm.setMode(new ModeInputGesture());
                    vm.activatePanel();
                    vm.update();
                });

        return view;
    }

    private static RecordCallVM findViewModel(RecordCall record) {
        return (RecordCallVM) App.current.getCurrentHistoryVM().findViewModel(record);
    }

    private static void addToCurrentHistory(RecordCall record) {
        App.current.getCurrentHistoryVM().add(record);
    }
}
