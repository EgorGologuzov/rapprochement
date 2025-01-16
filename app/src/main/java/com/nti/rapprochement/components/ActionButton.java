package com.nti.rapprochement.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatImageButton;

import com.nti.rapprochement.R;
import com.nti.rapprochement.utils.CompositeOnClickListener;

public class ActionButton extends androidx.appcompat.widget.AppCompatImageButton {
    final private CompositeOnClickListener onClickListener = new CompositeOnClickListener();

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
        initCompositeOnClickListener();
        initAnimation(context);
    }

    private void initCompositeOnClickListener() {
        super.setOnClickListener(onClickListener);
    }

    private void initAnimation(Context context) {
        final Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.action_button_click);
        this.setOnClickListener(v -> v.startAnimation(scaleAnimation));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener.registerListener(l);
    }
}