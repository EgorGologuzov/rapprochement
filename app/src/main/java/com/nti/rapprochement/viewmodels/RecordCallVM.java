package com.nti.rapprochement.viewmodels;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nti.rapprochement.App;
import com.nti.rapprochement.models.PanelCall;
import com.nti.rapprochement.models.RecordCall;
import com.nti.rapprochement.views.ModeShowText;
import com.nti.rapprochement.views.RecordCallView;

import java.util.Date;
import java.util.function.Function;

public class RecordCallVM extends RecordBaseVM {

    public static class CreateArgs {
        public final ViewGroup parent;
        public final RecordCallVM vm;
        public CreateArgs(ViewGroup parent, RecordCallVM vm) {
            this.parent = parent;
            this.vm = vm;
        }
    }

    public static abstract class Mode {
        public abstract View createInnerView(CreateArgs args);
        public abstract View createPanelView(CreateArgs args);
        public abstract boolean hasPanel(); // false если createPanelView возвращает null, иначе true
    }

    private final RecordCall model;
    private Mode mode;
    private PanelCall panel;

    public RecordCallVM(RecordCall model) {
        this.model = model;
        this.mode = new ModeShowText();
    }

    @Override
    public View createView(ViewGroup parent) {
        return RecordCallView.create(parent);
    }

    @Override
    public void bind(View view) {
        FrameLayout frame = (FrameLayout) view;

        if (frame.getChildCount() > 0) {
            View currentView = frame.getChildAt(0);
            frame.removeView(currentView);
        }

        if (mode != null) {
            View innerView = mode.createInnerView(new CreateArgs(frame, this));
            frame.addView(innerView);
        }
    }

    public void update() {
        App.current.getCurrentHistoryVM().notifyItemUpdate(model);
    }

    public void activatePanel() {
        App.current.setTemporaryPanel(panel);
    }

    public void deactivatePanel() {
        App.current.unsetTemporaryPanel(panel);
    }

    public void removeSelfFromHistory() {
        App.current.getCurrentHistoryVM().remove(model);
    }

    public void setMode(Mode mode) {
        this.mode = mode;

        if (mode != null && mode.hasPanel()) {
            panel = new PanelCall(model, mode::createPanelView);
        } else {
            panel = null;
        }
    }

    public String getText() {
        return model.text;
    }

    public void setText(String text) {
        this.model.text = text;
    }

    public Date getCreationTime() {
        return model.creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.model.creationTime = creationTime;
    }

    public RecordCall.SourceType getSourceType() {
        return model.sourceType;
    }
}
