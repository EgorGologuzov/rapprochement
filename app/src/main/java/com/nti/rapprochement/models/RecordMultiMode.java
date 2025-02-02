package com.nti.rapprochement.models;

import android.view.View;
import android.widget.FrameLayout;

import com.nti.rapprochement.App;
import com.nti.rapprochement.components.RecordMultiModeBaseVF;
import com.nti.rapprochement.components.ViewFactoryBase;

import java.util.Date;

public class RecordMultiMode extends RecordBase {

    public enum SourceType { Gesture, Sound, Text }

    private String text;
    private Date createTime;
    private SourceType sourceType;
    private RModeBase mode;
    private RModeBase boundMode;

    public RecordMultiMode(SourceType sourceType) {
        setViewFactory(new RecordMultiModeBaseVF());
        this.sourceType = sourceType;
        mode = new RModeBase(this);
        createTime = new Date();
    }

    @Override
    public void bind(View view) {
        FrameLayout frame = (FrameLayout) view;
        destroyBoundModeView(frame);

        View innerView = mode.createView(frame);
        frame.addView(innerView);

        boundMode = mode;
    }

    @Override
    public void destroySelf() {
        super.destroySelf();
        if (mode != null) {
            mode.destroySelf();
        }
    }

    @Override
    public void destroyView(View view) {
        super.destroyView(view);
        destroyBoundModeView((FrameLayout) view);
    }

    private void destroyBoundModeView(FrameLayout frame) {
        if (boundMode != null) {
            View currentView = frame.getChildAt(0);
            frame.removeView(currentView);
            boundMode.destroyView(currentView);
        }
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

        if (mode instanceof RModeShowText) {
            HistoryMain.current.removeFocus(this);
        } else {
            HistoryMain.current.requestFocus(this);
        }
    }

    public RModeBase getMode() {
        return mode;
    }

    public void setMode(RModeBase mode) {
        this.mode.destroySelf();
        this.mode = mode;
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
