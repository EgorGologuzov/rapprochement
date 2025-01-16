package com.nti.rapprochement;

import android.os.Bundle;

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
            public void handleOnBackPressed() {
                App.navigateBack();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void setOnFontSizeChangeHandler() {
        Settings.onFontSizeChange.add(fs -> recreate());
    }
}