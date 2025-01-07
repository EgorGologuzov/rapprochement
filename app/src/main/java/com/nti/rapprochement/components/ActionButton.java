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
        init(context, null, 0);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        initCompositeOnClickListener();
        initAttrs(attrs, defStyle);
        initAnimation(context);
    }

    private void initCompositeOnClickListener() {
        super.setOnClickListener(onClickListener);
    }

    private void initAttrs(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ActionButton, defStyle, 0);

        if (a.hasValue(R.styleable.ActionButton_iconReference)) {
            Drawable iconReference = a.getDrawable(R.styleable.ActionButton_iconReference);
            setIcon(iconReference);
            iconReference.setCallback(this);
        }

        if (a.hasValue(R.styleable.ActionButton_backgroundColor)) {
            int color = a.getColor(R.styleable.ActionButton_backgroundColor, 0);
            setBackgroundColor(color);
        }

        a.recycle();
    }

    private void initAnimation(Context context) {
        final Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.action_button_click);
        this.setOnClickListener(v -> v.startAnimation(scaleAnimation));
    }

    public void setIcon(Drawable reference) { setImageDrawable(reference); }

    public void setBackgroundColor(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        setBackground(drawable);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener.registerListener(l);
    }
}