package com.nti.rapprochement;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nti.rapprochement.adapters.RecordAdapter;
import com.nti.rapprochement.components.ActionButton;
import com.nti.rapprochement.consts.Keys;
import com.nti.rapprochement.models.History;
import com.nti.rapprochement.models.RecordBase;
import com.nti.rapprochement.models.RecordGesture;
import com.nti.rapprochement.models.RecordInfo;
import com.nti.rapprochement.models.RecordSettingsGroup;
import com.nti.rapprochement.models.RecordSound;
import com.nti.rapprochement.models.RecordText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecordAdapter adapter;
    private RecyclerView historyView;

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

        setButtonsHandlers();
        loadAndSetThemePreference();
        initRecycleView();
    }

    public void setButtonsHandlers() {
        findViewById(R.id.settingsButton).setOnClickListener(v -> {
            addRecord(new RecordSettingsGroup());
            toggleTheme();
        });

        findViewById(R.id.gestureButton).setOnClickListener(v -> addRecord(new RecordGesture()));
        findViewById(R.id.soundButton).setOnClickListener(v -> addRecord(new RecordSound()));
        findViewById(R.id.textButton).setOnClickListener(v -> addRecord(new RecordText()));
    }

    private void loadAndSetThemePreference() {
        SharedPreferences preferences = getSharedPreferences(Keys.AppPreferences, MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean(Keys.DarkMode, false);
        setTheme(isDarkMode);
    }

    private void saveThemePreference(boolean isDarkMode) {
        SharedPreferences preferences = getSharedPreferences(Keys.AppPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Keys.DarkMode, isDarkMode);
        editor.apply();
    }

    private void initRecycleView() {
        historyView = findViewById(R.id.historyView);
        adapter = new RecordAdapter(History.main.getRecords());

        historyView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        historyView.setLayoutManager(layoutManager);
    }

    public void toggleTheme() {
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        setTheme(currentNightMode != AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void setTheme(boolean isDarkMode) {
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode
                ? AppCompatDelegate.MODE_NIGHT_NO
                : AppCompatDelegate.MODE_NIGHT_YES);
        saveThemePreference(isDarkMode);
    }

    public void addRecord(RecordBase record) {
        History.main.push(record);
        adapter.notifyItemInserted(History.main.getSize() - 1);
        scrollToBottom();
    }

    public void scrollToBottom() {
        if (adapter.getItemCount() > 0) {
            historyView.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }
}