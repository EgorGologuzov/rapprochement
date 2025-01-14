package com.nti.rapprochement;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nti.rapprochement.data.Settings;
import com.nti.rapprochement.models.HistoryBase;
import com.nti.rapprochement.models.PanelBase;
import com.nti.rapprochement.navigation.Navigation;


public class MainActivity extends AppCompatActivity {
    private static int currentFontThemeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        initSettings();
        setFontTheme();

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initNavigationFrames();
        setBackButtonCallback();
    }

    private void initSettings() {
        Settings.init(this);
        Settings.setOnFontSizeChangeHandler(fs -> {
            currentFontThemeId = Settings.fontSizeToStyleId(fs);
            recreate();
        });
    }

    public void setFontTheme() {
        if (currentFontThemeId == -1) {
            currentFontThemeId = Settings.fontSizeToStyleId(Settings.getFontSize());
        }

        setTheme(currentFontThemeId);
    }

    private void initNavigationFrames() {
        FrameLayout historyFrame = findViewById(R.id.historyFrame);
        FrameLayout panelFrame = findViewById(R.id.panelFrame);

        HistoryBase.initParent(historyFrame);
        PanelBase.initParent(panelFrame);
        Navigation.initFrames(historyFrame, panelFrame);
    }

    private void setBackButtonCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.navigateBack();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}