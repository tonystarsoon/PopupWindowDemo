package it.cn.demo;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by tony on 2017/7/2.
 */

public class ScreenUtils {

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
