package com.nti.rapprochement.components;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import com.nti.rapprochement.utils.ViewsUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class ViewFactoryBase {
    private static final HashMap<View, ViewRegistrationData> allViews = new HashMap<>();

    public static void destroyAllDangerously() {
        HashMap<View, ViewRegistrationData> tmp = new HashMap<>(allViews);
        tmp.forEach((view, data) -> {
            data.viewFactory.destroy(view);
        });
    }

    protected Object currentOptionalData;

    public abstract View create(ViewGroup parent);

    public void destroy(View view) {
        currentOptionalData = unregisterAndGetOptionalData(view);
    }

    protected View createAndRegister(@LayoutRes int layoutId, ViewGroup parent) {
        return createAndRegister(layoutId, parent, null);
    }

    protected View createAndRegister(@LayoutRes int layoutId, ViewGroup parent, Object optionalData) {
        View view = ViewsUtils.createView(layoutId, parent);
        allViews.put(view, new ViewRegistrationData(this, optionalData));
        return view;
    }

    private Object unregisterAndGetOptionalData(View view) {
        ViewRegistrationData data = allViews.remove(view);
        return data != null ? data.optionalData : null;
    }

    private static class ViewRegistrationData {
        public final ViewFactoryBase viewFactory;
        public final Object optionalData;
        public ViewRegistrationData(ViewFactoryBase viewFactory, Object optionalData) {
            this.viewFactory = viewFactory;
            this.optionalData = optionalData;
        }
    }
}
