package com.nti.rapprochement.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class RoundPreview extends androidx.appcompat.widget.AppCompatImageView {

    private Runnable onImageChangeListener;

    public RoundPreview(Context context) {
        super(context);
    }

    public RoundPreview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundPreview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(roundBitmap(bm));
        if (onImageChangeListener != null) {
            onImageChangeListener.run();
        }
    }

    public void setOnImageChangeListener(Runnable listener) {
        onImageChangeListener = listener;
    }

    public void setMirrorMode(boolean needMirrorImage) {
        if (needMirrorImage) {
            setScaleY(-1);
        } else {
            setScaleY(1);
        }
    }

    private Bitmap roundBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
