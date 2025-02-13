package com.nti.rapprochement.views;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.nti.rapprochement.viewmodels.RecordCallVM;

public class HelperLiveText {

    public static class CreateArgs {
        public TextView target;
        public RecordCallVM vm;
        public @ColorInt int lightedTextBackground;
        public @ColorInt int lightedTextForeground;
    }

    public static HelperLiveText create(CreateArgs args) {
        return new HelperLiveText(args);
    }

    private final CreateArgs args;

    private HelperLiveText(CreateArgs args) {
        this.args = args;
    }

    public void setText(String newText) {
        String currentText = args.vm.getText() == null ? "" : args.vm.getText();
        newText = newText == null ? "" : newText;

        if (newText.equals(currentText)) {
            return;
        }

        int newIndex = -1;
        for (int i = 0; i < newText.length() && i < currentText.length(); i++) {
            if (newText.charAt(i) != currentText.charAt(i)) {
                newIndex = i;
                break;
            }
        }

        if (currentText.length() < newText.length() && newIndex == -1) {
            newIndex = currentText.length();
        }

        SpannableString span = new SpannableString(newText);

        if (newIndex != -1) {
            span.setSpan(
                    new BackgroundColorSpan(args.lightedTextBackground),
                    newIndex, newText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            span.setSpan(
                    new ForegroundColorSpan(args.lightedTextForeground),
                    newIndex, newText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        args.target.setText(span);
        args.vm.setText(newText);
    }
}
