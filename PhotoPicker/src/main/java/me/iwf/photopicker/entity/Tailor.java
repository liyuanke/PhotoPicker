package me.iwf.photopicker.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.iwf.photopicker.utils.DisplayUtils;

/**
 * Created by Administrator on 2018/6/26.
 */

public abstract class Tailor {
    protected int scal = 10;
    protected float rate = 1.0f / 1;
    protected int[] panelSzie;

    public void setPanelSzie(int width, int height) {
        this.panelSzie = new int[2];
        this.panelSzie[0] = width;
        this.panelSzie[1] = height;
    }

    /**
     * 根据分辨的大小进行调整
     */
    public abstract void adjustment(Context context);

    public abstract void onDraw(Canvas canvas, Paint paint);

    public abstract int[] getSize(Context context);

    protected int[] getDefault(Context context) {
        int size[] = DisplayUtils.getScreenSize(context);
        size[0] = size[0] - 2 * size[0] / scal;
        size[1] = (int) (rate * size[0]);
        return size;
    }
}
