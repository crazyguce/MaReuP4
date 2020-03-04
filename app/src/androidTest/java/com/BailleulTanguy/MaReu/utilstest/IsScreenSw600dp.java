package com.BailleulTanguy.MaReu.utilstest;

import android.util.DisplayMetrics;

import com.BailleulTanguy.MaReu.ui.activities.MainActivity;

/**
 * Created by Florence LE BOURNOT on 27/02/2020
 */
public class IsScreenSw600dp {

    public static boolean sIsScreenSw600dp;

    public static boolean isScreenSw600dp(MainActivity pActivity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        pActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }
}
