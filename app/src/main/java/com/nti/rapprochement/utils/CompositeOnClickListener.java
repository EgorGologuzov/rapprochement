package com.nti.rapprochement.utils;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CompositeOnClickListener implements View.OnClickListener {
    final private List<View.OnClickListener> registeredListeners = new ArrayList<>();

    public void registerListener (View.OnClickListener listener) {
        registeredListeners.add(listener);
    }

    @Override
    public void onClick(View v) {
        for(View.OnClickListener listener: registeredListeners) {
            listener.onClick(v);
        }
    }
}
