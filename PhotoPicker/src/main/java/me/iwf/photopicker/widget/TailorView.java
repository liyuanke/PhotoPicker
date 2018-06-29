package me.iwf.photopicker.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import me.iwf.photopicker.R;
import me.iwf.photopicker.entity.Tailor;

/**
 * Created by Administrator on 2018/6/26.
 */

public class TailorView extends View {
    private Tailor tailor;
    private Paint paint;

    public void setTailor(Tailor tailor) {
        this.tailor = tailor;
    }

    public TailorView(Context context) {
        super(context);
    }

    public TailorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TailorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        check();
        canvas.drawColor(getResources().getColor(R.color.__picker_half_transparent));
        tailor.onDraw(canvas, paint);
    }

    private void check() {
        assert tailor != null : "tailor is null";
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (tailor != null) {
            tailor.adjustment(getContext());
            if (paint == null) {
                paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(dp2px(5));
                tailor.setPanelSzie(getWidth(),getHeight());
            }
        }
    }

    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
