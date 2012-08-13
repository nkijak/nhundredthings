package com.kinnack.nthings.activity;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.kinnack.nthings.ProgressChart;
import com.kinnack.nthings.R;
import com.kinnack.nthings.StopWatch;
import com.kinnack.nthings.controller.FullWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.fragments.ExcerciseTabListener;
import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.Logg;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.level.Level;

public class WorkoutSettingsActivity extends SherlockFragmentActivity implements WorkoutActions {
    public static final String TAG = "dgmt:WorkoutSettings";
    private static final int COUNTER_INTENT = 100;
    private static final int TEST_INTENT = 150;
    private static final int REST_INTENT = 200;
    private static final int FINAL_TEST_INTENT=175;
    private static final int RESET_INTENT = 300;
    private static final String PUBLIC_FOLDER_PATH=Environment.getExternalStorageDirectory()+"/nhundredthings/";
    private static final String PUBLIC_FILE_PATH=PUBLIC_FOLDER_PATH+"/prefs_config.xml";
    private static final String PRIVATE_FILE_PATH = "/data/data/"+StopWatch.class.getPackage().getName()+"/shared_prefs/prefs_config.xml";
    public static String WORKOUT_TYPE = "workout-type";
    public static final String PREFS = "prefs_config";
    
    private FullWorkoutController fullWorkoutController;
    private Editor prefEditor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        Bundle extras = getIntent().getExtras();
        Type type = Type.valueOf(extras.getString(WORKOUT_TYPE));
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ExcerciseTabListener tabListener = new ExcerciseTabListener(this, Type.PUSHUP);
        
        actionBar.addTab(actionBar.newTab().setText("Push Ups").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Sit Ups").setTabListener(new ExcerciseTabListener(this, Type.SITUP)));
     
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefEditor = prefs.edit();
    }
    
   
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //((Spinner)findViewById(R.id.levelSelector)).s
    }
    
    
    
    
    public void resetSpinners() {
        ((Spinner)findViewById(R.id.dayWeekSelector)).setAdapter(null);
        ((Spinner)findViewById(R.id.levelSelector)).setAdapter(null);
//        dayWeekOrLevelChanged();
    }
    
   
  
    
   
    
    // ------------ APPLICATION FLOW ----------

	public void doFinalTest(View target_) {
        startFinalTestActivity();
    }

	public void showProgress(View target_) {
        showProgress(fullWorkoutController.getHistory());
    }
    
   

    @Override
    public void startCounterActivity() {
        Intent counterIntent = new Intent(this, fullWorkoutController.getCounterActivity());
        counterIntent.putExtra(CounterActivity.INIT_COUNT_KEY, fullWorkoutController.nextSet());
        counterIntent.putExtra(CounterActivity.SHOW_DONE, fullWorkoutController.isMaxSet());
   
        Log.d(TAG, "Intent about to start");
        startActivityForResult(counterIntent, COUNTER_INTENT);
        Log.d(TAG, "Intent started and returned");
    }

    @Override    
    public void startTestActivity() {
        Intent counterIntent = new Intent(this, fullWorkoutController.getCounterActivity());
        counterIntent.putExtra(CounterActivity.INIT_COUNT_KEY, 0);
        counterIntent.putExtra(CounterActivity.SHOW_DONE, true);
        counterIntent.putExtra(CounterActivity.IS_TEST, true);
        Log.d(TAG, "Intent about to start");
        startActivityForResult(counterIntent, TEST_INTENT);
        Log.d(TAG, "Intent started and returned");
    }
    
    @Override
    public void startFinalTestActivity() {
        Intent counterIntent = new Intent(this, fullWorkoutController.getCounterActivity());
        counterIntent.putExtra(CounterActivity.INIT_COUNT_KEY, 100);
        counterIntent.putExtra(CounterActivity.SHOW_DONE, true);
        counterIntent.putExtra(CounterActivity.IS_TEST, true);
        counterIntent.putExtra(CounterActivity.USE_SUBCOUNT, true);
        Log.d(TAG, "Intent about to start");
        startActivityForResult(counterIntent, FINAL_TEST_INTENT);
        Log.d(TAG, "Intent started and returned");
    }
    
    @Override
    public void startRestActivity() {
        Intent restIntent = new Intent(this, RestActivity.class);
        Log.d(TAG, "About to launch intnet for "+RestActivity.class.getName());
        restIntent.putExtra(RestActivity.REST_LENGTH, fullWorkoutController.nextSet());
        restIntent.putExtra(RestActivity.SETS_DONE, fullWorkoutController.completedSets());
        restIntent.putExtra(RestActivity.SETS_TO_GO, fullWorkoutController.incompleteSets());
        restIntent.putExtra(RestActivity.COUNT_TO_GO, fullWorkoutController.totalCountLeft());
        startActivityForResult(restIntent, REST_INTENT);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode_, int resultCode_, Intent data_) {
       Resources resources = getResources();
       switch (requestCode_) {
        case COUNTER_INTENT:
            // this was because the back button was pressed during a counter. FIXME do something better
            if (data_ == null) { 
                deleteAnyUnwantedLogs();
                return; 
            }
            Bundle extras = data_.getExtras();
            int count = extras.getInt(CounterActivity.MAX_COUNT);
            long avgTime = extras.getLong(CounterActivity.AVG_TIME);
            Logg currentLog =fullWorkoutController.getCurrentLog();
            currentLog.addCountAndTime(count, avgTime);
           
            if (!fullWorkoutController.hasNext()) { 
                fullWorkoutController.advanceDate();
                saveHistory(); 
                
                showProgress(fullWorkoutController.getHistory());
                
                shareResults(currentLog);
                finish();
                return;
                
            }
            startRestActivity();
            
            break;
        case REST_INTENT:
            startCounterActivity();
            break;
        case TEST_INTENT:
            if (data_ == null) { return; }
            int testCount = data_.getExtras().getInt(CounterActivity.MAX_COUNT);
            
            Level level = fullWorkoutController.getLevelForTestResult(testCount);
            if (level == null) { return; }
            fullWorkoutController.addTestResult(testCount).setCurrentLevel(level);
            saveHistory();
            Toast.makeText(this, level.toString(), Toast.LENGTH_SHORT).show();
            break;
        case FINAL_TEST_INTENT:
            if(data_ == null) { return; }
            testCount = data_.getExtras().getInt(CounterActivity.MAX_COUNT);
            long totalTime = data_.getExtras().getLong(CounterActivity.TOTAL_TIME);
            fullWorkoutController.addTestResult(testCount);
            if (testCount >= 100) {
                shareComplete(testCount, totalTime);
                String msg = String.format(resources.getString(R.string.final_complete_msg), 
                                            fullWorkoutController.getFinalTestCount(), 
                                            resources.getString(fullWorkoutController.getLabelResource()));
                showUserDialog(R.string.final_complete_title, msg);
                fullWorkoutController.markFinalComplete();
            } else {
                shareDNFFinal(testCount, totalTime);
                showUserDialog(R.string.final_DNF_title, resources.getString(R.string.final_DNF_msg));
            }
            fullWorkoutController.resetToWorkoutForFinal();
            saveHistory();
            break;
        case RESET_INTENT:
            if (data_ == null) {return;}
            if (data_.getExtras().getBoolean(ResetActivity.RESET,false)) {
                fullWorkoutController = null;
            }
            
            break;
        default:
            Log.w(TAG, "Got an unknown activity result. request["+requestCode_+"], result["+resultCode_+"]");
            break;
        }
        
        
        
    }



    private void deleteAnyUnwantedLogs() {
        fullWorkoutController.removeCurrentLog();
    }


  
    private void showUserDialog(int title_, String msg_) {
        
        new AlertDialog.Builder(this)
        .setTitle(title_)
        .setMessage(msg_)
        .setIcon(R.drawable.dialog)
        .setPositiveButton(R.string.final_complete_OK, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog_, int which_) {
                dialog_.dismiss();
            }
        }).show();
    }
    
    /**
     * @param currentLog
     */
    private void shareResults(Logg currentLog) {
        long roundedFrequency = Math.round(60000*currentLog.getAveragePushupFrequency());
        String typeLabel = getResources().getString(fullWorkoutController.getLabelResource());
        launchSharingChooser("My Latest DGMT! Results",
                getResources().getString(R.string.share_results_msg, currentLog.getTotalCount(), typeLabel ,roundedFrequency));
                

    }
    
    private void shareComplete(int totalCount_, long totalTime_) {
        long roundedFrequency = Math.round(60000.0*totalCount_/totalTime_);
        String typeLabel = getResources().getString(fullWorkoutController.getLabelResource());
        launchSharingChooser("Mission Accomplished!",
                getResources().getString(R.string.share_complete_msg, 
                                            fullWorkoutController.getFinalTestCount(),
                                            typeLabel,
                                            totalCount_, 
                                            roundedFrequency));
    }
    
    private void shareDNFFinal(int totalCount_, long totalTime_) {
        long roundedFrequency = Math.round(60000.0*totalCount_/totalTime_);
        String typeLabel = getResources().getString(fullWorkoutController.getLabelResource());
        launchSharingChooser("Almost There!",
                getResources().getString(R.string.share_dnf_final_msg, 
                        totalCount_,
                        typeLabel,
                        roundedFrequency));
    }
    
    
    private void launchSharingChooser(String subject_, String text_) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject_);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text_);
        startActivity(Intent.createChooser(shareIntent, "Share Results"));
    }
    
    
    
    // TODO move this out of here!
    private void saveHistory() {
        new AsyncTask<Void, Void, Boolean>() {
            protected Boolean doInBackground(Void... params_) {
                try {
                    fullWorkoutController.saveHistory(prefEditor);
                    
                    File externalFolder = new File(PUBLIC_FOLDER_PATH);
                    if (!externalFolder.exists()) { externalFolder.mkdir(); }
                    prefEditor.commit();
                    copyFile(new File(PRIVATE_FILE_PATH),new File(PUBLIC_FILE_PATH));
                    return true;
                } catch (JSONException e) {
                    Log.e(TAG,"Couldn't convert history to JSON! ",e);
                    return false;
                }
            }
            
            protected void onPostExecute(Boolean result) {                
                Toast.makeText(getApplicationContext(), "Error saving history", Toast.LENGTH_SHORT);
            }
        }.execute();
    }
    

    /**
     * @return TODO
     * 
     */
    private boolean copyFile(File originalFile_, File copyFile_) {
        try {
            Log.i("DGMT!Home.copyFile","Copying "+originalFile_+"(exists?"+originalFile_.exists()+") to "+copyFile_+" (exists?"+copyFile_.exists()+")");
            
            if (!copyFile_.exists()) {
                copyFile_.getParentFile().mkdirs();
                copyFile_.createNewFile();
            }
             
            FileOutputStream out = new FileOutputStream(copyFile_);
            FileInputStream in = new FileInputStream(originalFile_);
            
            
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel();

            long transferedBytes = outChannel.transferFrom(inChannel, 0, inChannel.size());

            inChannel.close();
            outChannel.close();
            in.close();
            out.close();

            return transferedBytes > 0;
        } catch (FileNotFoundException e1) {
           Log.i(TAG,"Could nto find file shared_prefs/prefs_config.xml",e1);
        } catch (IOException e) {
            Log.w(TAG, "ERROR trying to write preferences to disk",e);
        } 
        return false;
    }
    
    private void showProgress(History history_) {
        ProgressChart chart = new ProgressChart();
        Intent progressIntent = chart.progressChart(history_, this);
        startActivity(progressIntent);
    }
    
    //---- menu stuff ------
    @Override
    public boolean onOptionsItemSelected(MenuItem item_) {
        switch (item_.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item_);
        }
    }
    
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu_) {
    	// TODO Auto-generated method stub
    	return super.onPrepareOptionsMenu(menu_);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu_) {
       getSupportMenuInflater().inflate(R.menu.main,menu_);
       menu_.findItem(R.id.settings).setIntent(new Intent(this, SettingsActivity.class));
       menu_.findItem(R.id.reset).setEnabled(false).setVisible(false);
       return true;
    }


	public FullWorkoutController getFullWorkoutController() {
		return fullWorkoutController;
	}


	public void setFullWorkoutController(FullWorkoutController fullWorkoutController_) {
		fullWorkoutController = fullWorkoutController_;
	}


    
    
}
