package com.nti.rapprochement.models;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.components.FontSizeDialog;
import com.nti.rapprochement.components.RecordSettingsGroupView;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;

import java.util.ArrayList;

public class RecordSettingsGroup extends RecordBase {
    private final String title;
    private final ArrayList<SettingsParameter> parameters;

    public RecordSettingsGroup(String title, ArrayList<SettingsParameter> parameters) {
        this.title = title;
        this.parameters = parameters;
    }

    @Override
    public View getView(ViewGroup parent) {
        return RecordSettingsGroupView.create(parent, this);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<SettingsParameter> getParameters() {
        return parameters;
    }
}
