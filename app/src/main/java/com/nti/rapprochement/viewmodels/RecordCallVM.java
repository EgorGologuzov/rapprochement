package com.nti.rapprochement.viewmodels;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nti.rapprochement.App;
import com.nti.rapprochement.data.Permissions;
import com.nti.rapprochement.models.PanelCall;
import com.nti.rapprochement.models.RecordCall;
import com.nti.rapprochement.views.ModeShowText;
import com.nti.rapprochement.views.RecordCallView;

import java.util.Date;
import java.util.function.Consumer;

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
        public void dispose() {}
    }

    private final RecordCall model;
    private Mode mode;
    private PanelCall panel;
    private boolean isFocused;
    private Consumer<Permissions.RequestResult> permissionEventListener;

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

    @Override
    public void dispose() {
        super.dispose();
        if (mode != null) {
            mode.dispose();
        }
    }

    @Override
    public void handleGlobalEvent(Object args) {
        super.handleGlobalEvent(args);
        if (args instanceof Permissions.RequestResult && permissionEventListener != null) {
            permissionEventListener.accept((Permissions.RequestResult) args);
        }
    }

    public void finishInputOrShow() {
        if (mode == null || mode instanceof ModeShowText) {
            return;
        }

        if (TextUtils.isEmpty(getText())) {
            removeSelfFromHistory();
        } else {
            deactivatePanel();
            setMode(new ModeShowText());
            update();
        }
    }

    public void removeSelfFromHistory() {
        deactivatePanel();
        App.current.getCurrentHistoryVM().remove(model);
    }

    public void activateMode(Mode mode) {
        setMode(mode);
        activatePanel();
        update();
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

    public int getId() {
        return model.id;
    }

    public void setPermissionEventListener(Consumer<Permissions.RequestResult> permissionEventListener) {
        this.permissionEventListener = permissionEventListener;
    }

    private void update() {
        App.current.getCurrentHistoryVM().update(model);

        if (!(mode instanceof ModeShowText)) {
            requestFocus();
        }
    }

    private void activatePanel() {
        App.current.setTemporaryPanel(panel);
    }

    private void deactivatePanel() {
        App.current.unsetTemporaryPanel(panel);
    }

    private boolean isFocused() {
        return isFocused;
    }

    private void requestFocus() {
        if (!isFocused) {
            removeFocusFromCurrentFocused();
            isFocused = true;
        }
    }

    private void removeFocus() {
        if (isFocused) {
            finishInputOrShow();
            isFocused = false;
        }
    }

    private void setMode(Mode mode) {
        if (this.mode != null) {
            this.mode.dispose();
        }

        this.mode = mode;
        setPanelForCurrentMode(mode);
    }

    private void removeFocusFromCurrentFocused() {
        RecordCallVM currentFocused = (RecordCallVM) App.current.getCurrentHistoryVM()
                .findViewModel(vm -> vm instanceof RecordCallVM && ((RecordCallVM) vm).isFocused());

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
