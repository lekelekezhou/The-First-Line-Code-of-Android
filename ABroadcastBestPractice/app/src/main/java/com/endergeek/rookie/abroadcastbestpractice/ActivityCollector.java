package com.endergeek.rookie.abroadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsenhui on 10/27/16.
 */
public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity: activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
