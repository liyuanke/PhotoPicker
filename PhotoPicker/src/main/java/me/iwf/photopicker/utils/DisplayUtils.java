package me.iwf.photopicker.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/6/26.
 */

public class DisplayUtils {
    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        wm.getDefaultDisplay().getMetrics(dm);
        int[] size = new int[2];
        if (dm.widthPixels < dm.heightPixels) {
            size[0] = dm.widthPixels;
            size[1] = dm.heightPixels;
        } else {
            size[1] = dm.widthPixels;
            size[0] = dm.heightPixels;
        }
        return size;
    }
}
