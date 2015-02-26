package com.cozzbox.util;

import android.app.Activity;
import android.content.Context;

/**
 * Created by haramaki on 2015/02/26.
 */
public class DisplayUtil {

    public static float getDPtoPX(Context context, float dp) {
        float d = context.getResources().getDisplayMetrics().density;
        return dp * d;
    }

    public static float getDPtoPX(Activity activity, float dp) {
        float d = activity.getResources().getDisplayMetrics().density;
        return dp * d;
    }
}
