package me.iwf.photopicker.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Administrator on 2018/6/26.
 */

public class TailorRoundRect extends TailorRect {
    private int radius;

    public TailorRoundRect(int width, int height, int radius) {
        super(width, height);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, radius, radius, paint);
    }

}
