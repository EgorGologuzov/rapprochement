package com.nti.rapprochement.navigation;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentOnAttachListener;

import com.nti.rapprochement.R;
import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.HistoryMain;
import com.nti.rapprochement.models.PanelBase;
import com.nti.rapprochement.models.PanelMain;

import java.util.Stack;

public class Navigation {
    private static final Stack<HistoryBase> historyStack = new Stack<>();
    private static FrameLayout historyFrame;

    private static final Stack<PanelBase> panelStack = new Stack<>();
    private static FrameLayout panelFrame;

    public static void initFrames(FrameLayout historyFrame, FrameLayout panelFrame) {
        Navigation.historyFrame = historyFrame;
        Navigation.panelFrame = panelFrame;

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

    public static void pushHistory(HistoryBase history) {
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

    public static void popHistory() {
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

    public static void pushPanel(PanelBase panel) {
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

    public static void popPanel() {
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
