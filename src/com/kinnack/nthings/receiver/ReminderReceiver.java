package com.kinnack.nthings.receiver;

import java.util.Calendar;
import java.util.Date;

import com.kinnack.nthings.R;
import com.kinnack.nthings.activity.Home;
import com.kinnack.nthings.activity.WorkoutSettingsActivity;
import com.kinnack.nthings.controller.PushupWorkoutController;
import com.kinnack.nthings.controller.SitupWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {
    public static final int RESPONSE_CODE = 91191;
    public static final int NOTIFICATION_ID = 1200;

    @Override
    public void onReceive(Context context_, Intent intent_) {
        WorkoutController pushupController = new PushupWorkoutController();
        WorkoutController situpController = new SitupWorkoutController();
        SharedPreferences preferences = context_.getSharedPreferences(WorkoutSettingsActivity.PREFS, Context.MODE_PRIVATE);
        pushupController.forceReloadHistory(preferences);
        situpController.forceReloadHistory(preferences);
        
        boolean remindPushups = needsReminding(pushupController);
        boolean remindSitups = needsReminding(situpController);
        
        if (!remindPushups && !remindSitups) { return; }
        
        String message = "";
        String subject = "DGMT!";
        if (remindPushups && remindSitups) {
            message = "Get your workouts in!";
        } else if (remindPushups) {
            subject = "DGMT! Pushups";
            message = "Beat your face!";
        } else if (remindSitups) {
            subject = "DGMT! Situps";
            message = "Get some DGMT!";
        }
        
        sendNotification(context_, subject,message);
    }

    
    protected boolean needsReminding(WorkoutController controller_) {
        Date lastUsed = controller_.getHistory().getLastWorkout();
        if (lastUsed == null) { return false; } // if they never used it, don't remind
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastUsed);
        cal.add(Calendar.DATE, 2);
        Log.d("DGMT!ReminderReceiver.needsReminding", System.currentTimeMillis()+" >= "+cal.getTimeInMillis());
        
        return System.currentTimeMillis() >= cal.getTimeInMillis();
    }

    /**
     * @param context_
     * @param message_
     */
    private void sendNotification(Context context_, String subject_, String message_) {
        NotificationManager notificationManager = (NotificationManager)context_.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.notification_icon, message_, System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(context_, 0, new Intent(context_,Home.class), 0);
        notification.setLatestEventInfo(context_, subject_, message_, pendingIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
    
    
    
}
