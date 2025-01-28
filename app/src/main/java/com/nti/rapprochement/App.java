package com.nti.rapprochement;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.PanelBase;
import com.nti.rapprochement.models.PanelMain;

import java.util.Stack;

public class App {
    private static MainActivity mainActivity;

    private static final Stack<HistoryBase> historyStack = new Stack<>();
    private static FrameLayout historyFrame;

    private static final Stack<PanelBase> panelStack = new Stack<>();
    private static FrameLayout panelFrame;

    private static PanelBase temporaryPanel;

    public static void init(MainActivity mainActivity) {
        App.mainActivity = mainActivity;
        App.historyFrame = mainActivity.findViewById(R.id.historyFrame);
        App.panelFrame = mainActivity.findViewById(R.id.panelFrame);

        setCurrentHistoryAndPanelView();
    }

    public static Context getHistoryContext() { return historyFrame.getContext(); }

    public static Context getPanelContext() { return panelFrame.getContext(); }

    public static void navigate(HistoryBase history, PanelBase panel) {
        if (history != null) {
            pushHistory(history);
        }
        if (panel != null) {
            pushPanel(panel);
        }
    }

    public static void navigateBack() {
        int history = historyStack.size();
        int panel = panelStack.size();

        if (history > panel && history > 1) {
            popHistory();
        } else if (history < panel && panel > 1) {
            popPanel();
        } else if (history > 1 && panel > 1) {
            popHistory();
            popPanel();
        }
    }

    public static void setTemporaryPanel(PanelBase panel) {
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

    public static void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;

        View view = mainActivity.getCurrentFocus();
        if (view == null) view = new View(mainActivity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showToast(String text) {
        Toast.makeText(mainActivity, text, Toast.LENGTH_SHORT).show();
    }

    private static void setCurrentHistoryAndPanelView() {
        if (historyStack.isEmpty()) {
            historyStack.push(HistoryMain.shared);
            historyFrame.addView(HistoryMain.shared.getView());
        } else {
            historyFrame.addView(historyStack.peek().getView());
        }

        if (panelStack.isEmpty()) {
            panelStack.push(PanelMain.shared);
            panelFrame.addView(PanelMain.shared.getView());
        } else {
            panelFrame.addView(panelStack.peek().getView());
        }
    }

    private static void pushHistory(HistoryBase history) {
        View view = history.getView();

        if (historyFrame.getChildCount() > 0) {
            View currentView = historyFrame.getChildAt(historyFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.disappearance));
            historyFrame.removeView(currentView);
        }

        historyFrame.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.slide_left));
        view.setVisibility(View.VISIBLE);

        historyStack.push(history);
    }

    private static void popHistory() {
        if (!historyStack.isEmpty()) {
            View currentView = historyFrame.getChildAt(historyFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.slide_right));
            historyFrame.removeView(currentView);
            historyStack.pop();

            if (!historyStack.isEmpty()) {
                View previousView = historyStack.peek().getView();
                historyFrame.addView(previousView);
                previousView.setVisibility(View.INVISIBLE);
                previousView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.appearance));
                previousView.setVisibility(View.VISIBLE);
            }
        }
    }

    private static void pushPanel(PanelBase panel) {
        View view = panel.getView();

        if (panelFrame.getChildCount() > 0) {
            View currentView = panelFrame.getChildAt(panelFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.disappearance));
            panelFrame.removeView(currentView);
        }

        panelFrame.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.slide_up));
        view.setVisibility(View.VISIBLE);

        panelStack.push(panel);
    }

    private static void popPanel() {
        if (!panelStack.isEmpty()) {
            View currentView = panelFrame.getChildAt(panelFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.slide_down));
            panelFrame.removeView(currentView);
            panelStack.pop();

            if (!panelStack.isEmpty()) {
                View previousView = panelStack.peek().getView();
                panelFrame.addView(previousView);
                previousView.setVisibility(View.INVISIBLE);
                previousView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.appearance));
                previousView.setVisibility(View.VISIBLE);
            }
        }
    }
}
