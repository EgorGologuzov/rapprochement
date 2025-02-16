package com.nti.rapprochement.views;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nti.rapprochement.App;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Res;
import com.nti.rapprochement.utils.ViewsUtils;
import com.nti.rapprochement.viewmodels.RecordCallVM;

public class ModeInputText extends RecordCallVM.Mode {
    private static final int MAX_LENGTH = 500;

    @Override
    public View createInnerView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        View view = ViewsUtils.createView(R.layout.mode_input_text, parent);
        EditText edit = view.findViewById(R.id.editText);
        TextView counter = view.findViewById(R.id.symbolCount);

        edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_LENGTH) });
        edit.setText(vm.getText());
        counter.setText(formatCounterString(0));

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counter.setText(formatCounterString(s.length()));
                vm.setText(s.toString());
                if (s.length() == MAX_LENGTH) {
                    App.current.showToast(R.string.toast_is_max_text_length);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        edit.requestFocus();
        App.current.openKeyboard();

        return view;
    }

    @Override
    public View createPanelView(RecordCallVM.CreateArgs args) {
        ViewGroup parent = args.parent;
        RecordCallVM vm = args.vm;

        View view = ViewsUtils.createView(R.layout.panel_input_text, parent);

        view.findViewById(R.id.backButton)
                .setOnClickListener(v -> {
                    vm.removeSelfFromHistory();
                });

        view.findViewById(R.id.toTextButton)
                .setOnClickListener(v -> {
                    if (checkTextNotEmpty(vm)) {
                        vm.activateMode(new ModeShowText());
                    }
                });

        view.findViewById(R.id.toSoundButton)
                .setOnClickListener(v -> {
                    if (checkTextNotEmpty(vm)) {
                        vm.activateMode(new ModeShowSound());
                    }
                });

        view.findViewById(R.id.toGestureButton)
                .setOnClickListener(v -> {
                    if (checkTextNotEmpty(vm)) {
                        vm.activateMode(new ModeShowGesture());
                    }
                });

        return view;
    }

    @Override
    public boolean hasPanel() {
        return true;
    }

    @Override
    public void dispose() {
        super.dispose();
        App.current.hideKeyboard();
    }

    @SuppressLint("DefaultLocale")
    private static String formatCounterString(int count) {
        return String.format("%d/%d", count, MAX_LENGTH);
    }

    private boolean checkTextNotEmpty(RecordCallVM vm) {
        if (TextUtils.isEmpty(vm.getText())) {
            App.current.showToast(R.string.toast_text_not_recognized);
            return false;
        }
        return true;
    }
}
