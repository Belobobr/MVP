package com.mixailsednev.githubrepo.mvptabletphone.utils;

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Display {

    public static float getSmallestWidth(@NonNull WindowManager windowManager) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;

        return Math.min(widthDp, heightDp);
    }

    public static boolean phone(@NonNull WindowManager windowManager) {
        return getSmallestWidth(windowManager) < 720;
    }

    public static boolean tablet(@NonNull WindowManager windowManager) {
        return getSmallestWidth(windowManager) > 720;
    }

}
