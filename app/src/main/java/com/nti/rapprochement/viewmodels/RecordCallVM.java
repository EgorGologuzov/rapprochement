package com.nti.rapprochement.viewmodels;

import android.text.TextUtils;
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
    private boolean isFocused;

    public RecordCallVM(RecordCall model) {
        this.model = model;
        this.mode = new ModeShowText();
        this.panel = null;
        this.isFocused = false;
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

        if (!(mode instanceof ModeShowText)) {
            requestFocus();
        }
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

    public boolean isFocused() {
        return isFocused;
    }

    public void requestFocus() {
        if (!isFocused) {
            removeFocusFromCurrentFocused();
            isFocused = true;
        }
    }

    public void removeFocus() {
        if (isFocused) {
            finishInputOrShow();
            isFocused = false;
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        setPanelForCurrentMode(mode);
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

    private void finishInputOrShow() {
        if (mode == null || mode instanceof ModeShowText) {
            return;
        }

        if (TextUtils.isEmpty(getText())) {
            deactivatePanel();
            removeSelfFromHistory();
        } else {
            setMode(new ModeShowText());
            update();
            App.current.hideKeyboard();
        }
    }

    private void removeFocusFromCurrentFocused() {
        RecordCallVM currentFocused = (RecordCallVM) App.current.findViewModel(vm ->
                vm instanceof RecordCallVM && ((RecordCallVM) vm).isFocused()
        );

        if (currentFocused != null) {
            currentFocused.removeFocus();
        }
    }

    private void setPanelForCurrentMode(Mode mode) {
        if (mode != null && mode.hasPanel()) {
            panel = new PanelCall(model, mode::createPanelView);
        } else {
            panel = null;
        }
    }
}
