package com.example.imusic.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SpUtils {
    private final static String spName = "musicData";

    public static void saveGuideInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("guideFlag", true);
        editor.apply();
    }

    public static boolean getGuideInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
        return sp.getBoolean("guideFlag", false);
    }
}
