package me.iwf.photopicker.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import static me.iwf.photopicker.utils.DisplayUtils.getScreenSize;

/**
 * 圆形裁剪
 * Created by Administrator on 2018/6/26.
 */

public class TailorCircle extends Tailor {
    private int radius;

    public TailorCircle(int radius) {
        this.radius = radius;
    }

    @Override
    public void adjustment(Context context) {
        int screen[] = getScreenSize(context);
        int size[] = getSize(context);
        radius = size[0];
        if (radius > screen[0] || radius > screen[1]) {
            int interval = Math.min(screen[0], screen[1]) / scal;
            radius = Math.min(screen[0], screen[1]) - 2 * interval;
        }
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.drawCircle(0, 0, radius, paint);// 大圆
    }

    @Override
    public int[] getSize(Context context) {
        int[] size = getDefault(context);
        if (radius > 0) {
            size[0] = radius;
            size[1] = radius;
        }
        return size;
    }

}
