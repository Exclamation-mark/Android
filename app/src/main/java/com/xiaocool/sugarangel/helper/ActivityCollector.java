package com.xiaocool.sugarangel.helper;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzh on 16/11/22.
 */

public class ActivityCollector {
    private static ActivityCollector instance;

    public static ActivityCollector getInstance() {
        if (instance == null) {
            instance = new ActivityCollector();
        }
        return instance;
    }

    public List<Activity> activities = new ArrayList<>();

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void finishAll() {
        for (Activity activity : activities) {
//            if (!activity.isFinishing()) {
                activity.finish();
//            }
        }
        System.exit(0);
    }
}
