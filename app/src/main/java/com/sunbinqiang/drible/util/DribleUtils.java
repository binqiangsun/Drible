package com.sunbinqiang.drible.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by sunbinqiang on 04/06/2017.
 */

public class DribleUtils {

    /**
     * @param context
     * @return
     */
    public static Activity getActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivityFromContext(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
