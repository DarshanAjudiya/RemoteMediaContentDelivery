package com.example.projectapp.extras;


import android.content.Context;

public class convert {

    public static long dptopx(Context context, double dp)
    {
        Float density=context.getResources().getDisplayMetrics().density;
        return  Math.round(dp * density + 0.5);
    }
}
