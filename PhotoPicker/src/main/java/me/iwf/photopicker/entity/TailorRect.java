package me.iwf.photopicker.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import static me.iwf.photopicker.utils.DisplayUtils.getScreenSize;

/**
 * 矩形裁剪
 * Created by Administrator on 2018/6/26.
 */

public class TailorRect extends Tailor {
    protected int width;
    protected int height;
    private int left;
    private int right;
    private int top;
    private int bottom;

    public TailorRect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 根据分辨的大小进行调整
     */
    public void adjustment(Context context) {
        int[] screenSize = getScreenSize(context);
        int screenWidth = screenSize[0];
        int screenHeight = screenSize[1];
        int interval = screenWidth / scal;
        int size[] = getSize(context);
        width = size[0];
        height = size[1];
        if ((width / screenWidth) > (height / screenHeight)) {//宽度缩放比例大于高度,按高度的比例缩放
            if (screenWidth < width) {
                width = screenWidth - 2 * interval;
                height = (int) (width * rate);
            }
        } else {
            if (screenHeight < height) {
                interval = screenHeight / scal;
                height = screenHeight - 2 * interval;
                width = (int) (height / rate);
            }
        }
        left = interval;
        right = width + left;
        top = (screenHeight - height) / 2;
        bottom = top + height;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.clipRect(left, top, right, bottom);
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public int[] getSize(Context context) {
        int[] size = getDefault(context);
        if (width > 0 && height > 0) {
            size[0] = width;
            size[1] = height;
        }
        return size;
    }
}
