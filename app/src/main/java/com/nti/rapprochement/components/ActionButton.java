package com.nti.rapprochement.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.Event;

public class ActionButton extends androidx.appcompat.widget.AppCompatImageButton {
    final public Event<View> onCLick = new Event<>();

    public ActionButton(Context context) {
        super(context);
        init(context);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        initOnClickListener();
        initAnimation(context);
    }

    private void initOnClickListener() {
        super.setOnClickListener(onCLick::call);
    }

    private void initAnimation(Context context) {
        final Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.action_button_click);
        this.setOnClickListener(v -> v.startAnimation(scaleAnimation));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l != null) {
            onCLick.add(l::onClick);
        }
    }
}