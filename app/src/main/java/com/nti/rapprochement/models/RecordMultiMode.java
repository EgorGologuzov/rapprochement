package com.nti.rapprochement.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.components.RecordDefaultView;

import java.util.Date;

public class RecordMultiMode extends RecordBase {

    public enum SourceType { Gesture, Sound, Text }

    private String text;
    private Date createTime;
    private SourceType sourceType;
    private RModeBase mode;

    public RecordMultiMode(SourceType sourceType) {
        this.sourceType = sourceType;
        mode = new RModeBase();
        createTime = new Date();
    }

    @Override
    public View getView(ViewGroup parent) {
        return RecordDefaultView.create(parent, R.string.multi_mode_record_view_not_set);
    }

    @Override
    public void bind(View view) {
        FrameLayout frame = (FrameLayout) view;
        unsetCurrentMode(frame);

        View innerView = mode.getView(frame, this);
        frame.addView(innerView);
    }

    private void unsetCurrentMode(FrameLayout frame) {
        if (mode != null) {
            View currentView = frame.getChildAt(0);
            frame.removeView(currentView);
        }
    }

    public void setMode(RModeBase mode) {
        this.mode = mode;
    }

    public void activatePanel() {
        if (mode != null) {
            PanelBase panel = mode.getPanel(this);
            App.setTemporaryPanel(panel);
        }
    }

    public void deactivatePanel() {
        App.setTemporaryPanel(null);
    }

    public void update() {
        this.onUpdate.call(this);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }
}
