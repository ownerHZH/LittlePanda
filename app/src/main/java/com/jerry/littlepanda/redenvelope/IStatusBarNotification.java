package com.jerry.littlepanda.redenvelope;

import android.app.Notification;

/**

 */
public interface IStatusBarNotification {

    String getPackageName();
    Notification getNotification();
}
