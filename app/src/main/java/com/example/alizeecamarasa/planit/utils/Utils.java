package com.example.alizeecamarasa.planit.utils;

import android.app.Activity;

/**
 * Created by alizeecamarasa on 26/02/15.
 */
public class Utils {
    public static int getPixelValue(int dp, Activity context) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
