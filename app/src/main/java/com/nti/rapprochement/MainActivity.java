package com.nti.rapprochement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.components.ActionButton;
import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.models.History;
import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.models.RecordGesture;
import com.nti.rapprochement.models.RecordSettingsGroup;
import com.nti.rapprochement.models.RecordSound;
import com.nti.rapprochement.models.RecordText;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private static MainActivity shared;

    private final Stack<View> historyStack = new Stack<>();
    private FrameLayout historyFrame;

    private final Stack<View> panelStack = new Stack<>();
    private FrameLayout panelFrame;

    public static void Navigate(View history, View panel) {
        if (history != null) {
            shared.pushHistory(history);
        }
        if (panel != null) {
            shared.pushPanel(panel);
        }
    }

    public static void NavigateBack() {
        int history = shared.getHistoryStackSize();
        int panel = shared.getPanelStackSize();

        if (history > panel) {
            shared.popHistory();
        } else if (history < panel) {
            shared.popPanel();
        } else {
            shared.popHistory();
            shared.popPanel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shared = this;
        initSettings();
        initNavigationFrames();
        setButtonsHandlers();
    }

    public void initSettings() {
        Settings.init(this);
    }

    public void initNavigationFrames() {
        historyFrame = findViewById(R.id.historyFrame);
        panelFrame = findViewById(R.id.panelFrame);

        RecyclerView mainHistoryView = findViewById(R.id.mainHistoryView);
        LinearLayout mainPanelView = findViewById(R.id.mainPanelView);

        historyStack.push(mainHistoryView);
        panelStack.push(mainPanelView);

        History.main.initView(this, mainHistoryView);
    }

    public void setButtonsHandlers() {
        findViewById(R.id.settingsButton).setOnClickListener(v -> {
//            Settings.setTheme(!Settings.getTheme());

            View settings = History.settings.getView(this);
            View panel = ViewsUtils.inflateLayout(this, R.layout.panel_settings);

            ActionButton button = panel.findViewById(R.id.backButton);
            button.setOnClickListener(v2 -> NavigateBack());

            Navigate(settings, panel);
        });

        findViewById(R.id.gestureButton).setOnClickListener(v -> History.main.push(new RecordGesture()));
        findViewById(R.id.soundButton).setOnClickListener(v -> History.main.push(new RecordSound()));
        findViewById(R.id.textButton).setOnClickListener(v -> History.main.push(new RecordText()));
    }

    public void pushHistory(View view) {
        if (historyFrame.getChildCount() > 0) {
            View currentView = historyFrame.getChildAt(historyFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.disappearance));
            historyFrame.removeView(currentView);
        }

        historyFrame.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.slide_left));
        view.setVisibility(View.VISIBLE);

        historyStack.push(view);
    }

    public void popHistory() {
        if (!historyStack.isEmpty()) {
            View currentView = historyStack.pop();
            currentView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.slide_right));
            historyFrame.removeView(currentView);

            if (!historyStack.isEmpty()) {
                View previousView = historyStack.peek();
                historyFrame.addView(previousView);
                previousView.setVisibility(View.INVISIBLE);
                previousView.startAnimation(AnimationUtils.loadAnimation(historyFrame.getContext(), R.anim.appearance));
                previousView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void pushPanel(View view) {
        if (panelFrame.getChildCount() > 0) {
            View currentView = panelFrame.getChildAt(panelFrame.getChildCount() - 1);
            currentView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.disappearance));
            panelFrame.removeView(currentView);
        }

        panelFrame.addView(view);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.slide_up));
        view.setVisibility(View.VISIBLE);

        panelStack.push(view);
    }

    public void popPanel() {
        if (!panelStack.isEmpty()) {
            View currentView = panelStack.pop();
            currentView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.slide_down));
            panelFrame.removeView(currentView);

            if (!panelStack.isEmpty()) {
                View previousView = panelStack.peek();
                panelFrame.addView(previousView);
                previousView.setVisibility(View.INVISIBLE);
                previousView.startAnimation(AnimationUtils.loadAnimation(panelFrame.getContext(), R.anim.appearance));
                previousView.setVisibility(View.VISIBLE);
            }
        }
    }

    public int getHistoryStackSize() {
        return historyStack.size();
    }

    public int getPanelStackSize() {
        return panelStack.size();
    }
}