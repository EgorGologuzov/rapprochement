package com.nti.rapprochement;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.PanelBase;
import com.nti.rapprochement.models.PanelMain;
import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.viewmodels.HistoryBaseVM;
import com.nti.rapprochement.viewmodels.RecordBaseVM;

import java.util.Stack;
import java.util.function.Function;

// AppViewModel
public class App {

    public static App current;

    private static class AppModel {
        public final static AppModel current = new AppModel();
        public final Stack<HistoryBase> historyStack = new Stack<>();
        public final Stack<PanelBase> panelStack = new Stack<>();
        public final HistoryMain historyMain = new HistoryMain();
        public final PanelMain panelMain = new PanelMain();
        private AppModel() {
            historyStack.push(historyMain);
            panelStack.push(panelMain);
        }
    }

    public static void init(MainActivity mainActivity) {
        current = new App(mainActivity, AppModel.current);
    }

    private final MainActivity mainActivity;
    private final AppModel appModel;
    private PanelBase temporaryPanel;
    private HistoryBaseVM currentHistoryVM;

    private App(MainActivity mainActivity, AppModel appModel) {
        this.mainActivity = mainActivity;
        this.appModel = appModel;

        FrameLayout historyFrame = mainActivity.findViewById(R.id.historyFrame);
        currentHistoryVM = appModel.historyStack.peek().createViewModel();
        View lastHistoryView = currentHistoryVM.createView(historyFrame);
        historyFrame.addView(lastHistoryView);

        FrameLayout panelFrame = mainActivity.findViewById(R.id.panelFrame);
        View lastPanelView = appModel.panelStack.peek().createViewModel().createView(panelFrame);
        panelFrame.addView(lastPanelView);
    }

    public Context getDialogContext() {
        FrameLayout historyFrame = mainActivity.findViewById(R.id.historyFrame);
        return historyFrame.getContext();
    }

    public HistoryBaseVM getCurrentHistoryVM() {
        return currentHistoryVM;
    }

    public void recreateMainActivity() {
        mainActivity.recreate();
    }

    public void showToast(@StringRes int resId) {
        Toast.makeText(mainActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        Toast.makeText(mainActivity, text, Toast.LENGTH_SHORT).show();
    }

    public void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;

        View view = mainActivity.getCurrentFocus();
        if (view == null) view = new View(mainActivity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void runOnUiThread(Runnable action) {
        mainActivity.runOnUiThread(action);
    }

    public void navigate(HistoryBase history, PanelBase panel) {
        if (history != null) {
            pushHistory(history);
        }
        if (panel != null) {
            pushPanel(panel);
        }
    }

    public void navigateBack() {
        int history = appModel.historyStack.size();
        int panel = appModel.panelStack.size();

        if (history > panel && history > 1) {
            popHistory();
        } else if (history < panel && panel > 1) {
            popPanel();
        } else if (history > 1 && panel > 1) {
            popHistory();
            popPanel();
        }
    }

    public void setTemporaryPanel(PanelBase panel) {
        if (panel != null && temporaryPanel == null) {
            pushPanel(panel);
        } else if (panel == null && temporaryPanel != null) {
            popPanel();
        } else if (panel != null && temporaryPanel != null) {
            popPanel();
            pushPanel(panel);
        }

        temporaryPanel = panel;
    }

    public void unsetTemporaryPanel(PanelBase panel) {
        if (temporaryPanel == panel) {
            setTemporaryPanel(null);
        }
    }

    private void pushHistory(HistoryBase history) {
        FrameLayout historyFrame = mainActivity.findViewById(R.id.historyFrame);
        currentHistoryVM = history.createViewModel();
        View view = currentHistoryVM.createView(historyFrame);

        if (historyFrame.getChildCount() > 0) {
            View currentView = historyFrame.getChildAt(historyFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.disappearance));
            historyFrame.removeView(currentView);
        }

        historyFrame.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.slide_left));
        view.setVisibility(View.VISIBLE);

        appModel.historyStack.push(history);
    }

    private void popHistory() {
        if (!appModel.historyStack.isEmpty()) {
            FrameLayout historyFrame = mainActivity.findViewById(R.id.historyFrame);
            View currentView = historyFrame.getChildAt(historyFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.slide_right));
            historyFrame.removeView(currentView);
            appModel.historyStack.pop();

            if (!appModel.historyStack.isEmpty()) {
                currentHistoryVM = appModel.historyStack.peek().createViewModel();
                View previousView = currentHistoryVM.createView(historyFrame);
                historyFrame.addView(previousView);
                previousView.setVisibility(View.INVISIBLE);
                previousView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.appearance));
                previousView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void pushPanel(PanelBase panel) {
        FrameLayout panelFrame = mainActivity.findViewById(R.id.panelFrame);
        View view = panel.createViewModel().createView(panelFrame);

        if (panelFrame.getChildCount() > 0) {
            View currentView = panelFrame.getChildAt(panelFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.disappearance));
            panelFrame.removeView(currentView);
        }

        panelFrame.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.slide_up));
        view.setVisibility(View.VISIBLE);

        appModel.panelStack.push(panel);
    }

    private void popPanel() {
        if (!appModel.panelStack.isEmpty()) {
            FrameLayout panelFrame = mainActivity.findViewById(R.id.panelFrame);
            View currentView = panelFrame.getChildAt(panelFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.slide_down));
            panelFrame.removeView(currentView);
            appModel.panelStack.pop();

            if (!appModel.panelStack.isEmpty()) {
                View previousView = appModel.panelStack.peek().createViewModel().createView(panelFrame);
                panelFrame.addView(previousView);
                previousView.setVisibility(View.INVISIBLE);
                previousView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.appearance));
                previousView.setVisibility(View.VISIBLE);
            }
        }
    }
}
