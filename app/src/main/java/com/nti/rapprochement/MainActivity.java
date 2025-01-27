package com.nti.rapprochement;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.data.Settings;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        Settings.init(this);
        setFontSize();
        setDarkMode();

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        App.init(this);
        Res.init(this);
        setBackButtonCallback();
        setOnFontSizeChangeHandler();
        setAdaptiveLayoutListener();
    }

    private void setFontSize() {
        int themeId = Settings.fontSizeToStyleId(Settings.getFontSize());
        setTheme(themeId);
    }

    private void setDarkMode() {
        Settings.setTheme(Settings.getTheme());
    }

    private void setBackButtonCallback() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void setOnFontSizeChangeHandler() {
        Settings.onFontSizeChange.add(fs -> recreate());
    }

    private void setAdaptiveLayoutListener() {
        final View activityRootView = findViewById(android.R.id.content);
        final LinearLayout adaptiveLayout = findViewById(R.id.adaptiveLayout);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            activityRootView.getWindowVisibleDisplayFrame(r);

            int actualHeight = r.bottom - r.top;
            int adaptiveLayoutHeight = adaptiveLayout.getHeight();

            if (adaptiveLayoutHeight != actualHeight) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) adaptiveLayout.getLayoutParams();
                params.height = actualHeight;
                adaptiveLayout.setLayoutParams(params);
            }
        });
    }
}