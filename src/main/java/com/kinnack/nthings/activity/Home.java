package com.kinnack.nthings.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kinnack.nthings.ProgressChart;
import com.kinnack.nthings.R;
import com.kinnack.nthings.StopWatch;
import com.kinnack.nthings.controller.PushupWorkoutController;
import com.kinnack.nthings.controller.SitupWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.helper.CounterActivityManager;
import com.kinnack.nthings.helper.PrettyDateAndTime;
import com.kinnack.nthings.model.DayAndWeek;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.LevelSelectionViewAdapter;
import com.kinnack.nthings.model.Logg;
import com.kinnack.nthings.model.Test;
import com.kinnack.nthings.model.WorkoutSelectionViewAdapter;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;
import com.kinnack.nthings.receiver.ReminderReceiver;

public class Home extends Activity {
    public static final String TAG = "dgmt:HOME";
    private static final int RESET_INTENT = 300;
    private WorkoutController pushupController;
    private WorkoutController situpController;
    
    
    private TextView pushupLastUse;
    private TextView pushupLastCount;
    private TextView pushupTotalValue;
    
    private TextView situpLastUse;
    private TextView situpLastCount;
    private TextView situpTotalValue;
    
    private Editor prefEditor;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        pushupLastUse = (TextView)findViewById(R.id.pushupLastUse);
        pushupLastCount = (TextView)findViewById(R.id.pushupLastCount);
        pushupTotalValue = (TextView)findViewById(R.id.pushupTotalValue);
        
        situpLastUse = (TextView)findViewById(R.id.situpLastUse);
        situpLastCount = (TextView)findViewById(R.id.situpLastCount);
        situpTotalValue = (TextView)findViewById(R.id.situpTotalValue);
        
        pushupController = new PushupWorkoutController();
        situpController = new SitupWorkoutController();

        setupAlarm();
        
    }
    
    private void setupAlarm() {
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ReminderReceiver.RESPONSE_CODE,  intent, PendingIntent.FLAG_NO_CREATE);

        boolean shouldHaveReminders = shouldHaveReminders();
        if (shouldHaveReminders && pendingIntent == null) { 
            // turn them on
            pendingIntent = PendingIntent.getBroadcast(this, ReminderReceiver.RESPONSE_CODE,  intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime(),AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
        } else if (!shouldHaveReminders && pendingIntent != null) {
            // turn them off!
            pendingIntent.cancel();
        }
    }
    
    private boolean shouldHaveReminders() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean(getResources().getString(R.string.reminders_setting_key), true);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        SharedPreferences preferences = getSharedPreferences(WorkoutSettingsActivity.PREFS, Context.MODE_PRIVATE);
        pushupController.forceReloadHistory(preferences);
        situpController.forceReloadHistory(preferences);
        
        pushupLastUse.setText(formatDate(pushupController.getHistory().getLastWorkout()));
        pushupLastCount.setText(pushupController.getCurrentLog().getTotalCount()+"");
        pushupTotalValue.setText(pushupController.getTotalCount()+"");
        
        situpLastUse.setText(formatDate(situpController.getHistory().getLastWorkout()));
        situpLastCount.setText(situpController.getCurrentLog().getTotalCount()+"");
        situpTotalValue.setText(situpController.getTotalCount()+"");
    }
    
    private String formatDate(Date date_) {
        if (date_ == null) { return "?"; }
        return PrettyDateAndTime.format(date_);
    }
    
    public void doPushups(View target_) {
        doExercise(Type.PUSHUP);
    }

    public void doSitups(View target_) {
        doExercise(Type.SITUP);
    }
    
    public void doExercise(Type type_) {
        Intent intent = new Intent(this,WorkoutSettingsActivity.class);
        intent.putExtra(WorkoutSettingsActivity.WORKOUT_TYPE, type_.toString());
        startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu_) {
       getMenuInflater().inflate(R.menu.main,menu_);
       menu_.findItem(R.id.settings).setIntent(new Intent(this, SettingsActivity.class));
       menu_.findItem(R.id.reset).setIntent(new Intent(this, ResetActivity.class));
       return true;
    }
    
  
    
    
    private boolean createFile(File file_) throws IOException{
        if (file_.exists()) { return true; }
        if (!file_.getParentFile().exists()) { createFile(file_.getParentFile()); }
        return file_.createNewFile();
    }
}