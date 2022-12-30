package com.kinnack.nthings.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.kinnack.nthings.R;
import com.kinnack.nthings.StopWatch;
import com.kinnack.nthings.model.SoundAlert;
import com.kinnack.nthings.model.colyseus.MyRoomState;

import io.colyseus.Client;
import io.colyseus.Room;
import io.colyseus.util.callbacks.Function1Void;

import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.os.PowerManager.SCREEN_DIM_WAKE_LOCK;
import io.colyseus.serializer.schema.types.SchemaReflection;

public class CounterActivity extends Activity implements OnSeekBarChangeListener, Function1Void<Room<MyRoomState>>{
    public static final String INIT_COUNT_KEY = "com.kinnack.nthings.init_count";
    public static final String SHOW_DONE = "com.kinnack.nthings.show_done";
    public static final String MAX_COUNT = "com.kinnack.nthing.max_count";
    public static final String AVG_TIME = "com.kinnack.nthing.time.avg";
    public static final String TOTAL_TIME = "com.kinnack.nthing.time.total";
    public static final String HISTORY = "com.kinnack.nthing.history";
    public static final String IS_TEST = "com.kinnack.nthing.is_test";
    public static final String USE_SUBCOUNT = "com.kinnack.nthing.use_subcount";
    private int count = 0;
    protected int increment = 1;
    protected int neededCount = 0;
    protected StopWatch stopWatch;
    protected long sumTimeBetweenCounts = 0;
    private boolean useSubcount = false;
    protected SoundAlert soundAlert;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        Bundle extras = getIntent().getExtras();
        neededCount = extras.getInt(INIT_COUNT_KEY);
        count = neededCount;
        boolean showDone = extras.getBoolean(SHOW_DONE);
        
        
        if (!showDone) { increment = -1; }
        if (showDone) { count = 0; }
        setContentView(getLayout());
        TextView totalCount = (TextView) findViewById(R.id.TotalCount);
        String countText = (count == 0 ? "at least "+neededCount : count+"");
        if (count == 0 && neededCount == 0) countText = getResources().getText(R.string.count_for_test_msg).toString();
        totalCount.setText(countText);
        if (showDone) {
            showQuitingOptions();
        }
        additionalViewConfiguration(extras);
        soundAlert = new SoundAlert(PreferenceManager.getDefaultSharedPreferences(this), this);
        
        if (extras.getBoolean(IS_TEST)) {
            dialogToUser(R.string.is_test_title,R.string.is_test_msg);
        }

        Client client = new Client("ws://192.168.1.177:2567");
// FIXME implementation broken, casts LinkedHashMap to AvailableRoom without actual conversion
//        client.getAvailableRooms("my_room", rooms -> {
//            rooms.stream().forEach(room -> Log.d("AvailalbeRooms", room.getRoomId()));
//        });
        SchemaReflection sr = new SchemaReflection();
        client.joinOrCreate(MyRoomState.class,
                "my_room",
                this,
                Throwable::printStackTrace);
        
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        keepScreenAlive();
    }

    /**
     * @param extras
     */
    protected void additionalViewConfiguration(Bundle extras) {
        if (extras.getBoolean(USE_SUBCOUNT)) {
            findViewById(R.id.SubCountProgress).setVisibility(View.VISIBLE);
            useSubcount = true;
        }
    }


    /**
     * 
     */
    protected void showQuitingOptions() {
        ((SeekBar)findViewById(R.id.Done)).setOnSeekBarChangeListener(this);
        findViewById(R.id.Done).setVisibility(View.VISIBLE);
    }
    
    protected void reachedMinimumRequired() {
        findViewById(R.id.Done).setEnabled(true);
    }


    /**
     * @return
     */
    protected int getLayout() {
        return R.layout.counter;
    }


    /**
     * 
     */
    private void dialogToUser(int title_, int message_) {
        new AlertDialog.Builder(this)
            .setTitle(title_)
            .setMessage(message_)
            .setIcon(R.drawable.dialog)
            .setPositiveButton(R.string.positive, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog_, int which_) {
                    dialog_.dismiss();
                }
            })
            .show();
    }
    
    public void countButtonClick(View target)
    {
        count();
        soundAlert.progressBeep();
        final Button c = (Button) findViewById(R.id.Count);
        //Disable the count button, and re-enable in 250 milliseconds.
        c.setClickable(false);
        c.getHandler().postDelayed(new Runnable(){
           	public void run()
           	{
          		c.setClickable(true);
        	}
        }, 250);
    }
    
    // ??? Is it necessary to stop the counter each time?
    protected void count() {
        countRep();
        incrementProgress();
        
        stopWatch.stop();
        int reps = count;
        if (increment == -1) {
            reps = neededCount - count;
        }
        sumTimeBetweenCounts += stopWatch.getElapsedTime();
        
        
        if ((increment  == -1) && (count == 0)) {
            setResult(RESULT_OK,createIntentWithStats(reps));
            soundAlert.finishedBeep();
            finish();
        }
        
        if (neededCount - count <= 0) { reachedMinimumRequired(); }
        
        stopWatch.start();
    }


    /**
     * 
     */
    private void incrementProgress() {
        TextView totalCount = (TextView) findViewById(R.id.TotalCount);
        totalCount.setText(""+(count+=increment));

        ProgressBar progress = (ProgressBar) findViewById(R.id.CountProgress);
        int progressPercent = getProgressPercent();
        
        if (useSubcount) {
            ProgressBar subProgress = (ProgressBar)findViewById(R.id.SubCountProgress);
            subProgress.setProgress((int)Math.floor(progressPercent));
            progress.setProgress((progressPercent % 10)*10);
        } else {
            progress.setProgress(progressPercent);
        }
    }
    
    public void done(View target) {
        stopWatch.stop();
        setResult(RESULT_OK,createIntentWithStats(count));
        finish();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundAlert.cleanup();
        wakeLock.release();
    }
    
    private int getProgressPercent() {
        int retval = 0;
        if (increment == -1) {
            
            retval = (int) ((1-count*1.0/neededCount)*100);
        } else {
            double percentLeft = count*1.0/neededCount*100;
            retval = (int) (percentLeft > 100 ? 100 : percentLeft);
        }
        return retval;
    }
    
    protected Intent createIntentWithStats(int reps_) {
        Intent intent = new Intent();
        intent.putExtra(MAX_COUNT, reps_);
        intent.putExtra(AVG_TIME, (reps_ == 0 ? 0 : sumTimeBetweenCounts/reps_));
        intent.putExtra(TOTAL_TIME, sumTimeBetweenCounts);
        return intent;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar_, int progress_, boolean fromUser_) {
        if (progress_ >= 90) {
            seekBar_.setEnabled(false);
            done(null);
        }
        
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar_) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar_) {
        if (seekBar_.getProgress() >= 90) {
            seekBar_.setProgress(0);
        }
        
    }
    
    private void keepScreenAlive() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(SCREEN_DIM_WAKE_LOCK|ON_AFTER_RELEASE, "RestActivity.screenOn");
        wakeLock.acquire();
    }


    private static final String ROOM_TAG = "ROOM";
    private Room<MyRoomState> _room = null;
    @Override
    public void invoke(Room<MyRoomState> room) {
        Log.i("CLIENT", "joined "+room.getName()+" with sess="+room.getSessionId());
        _room = room;

        room.setOnJoin(() -> {
            Log.d(ROOM_TAG, "onJoin()");
        });

        room.setOnLeave(code -> {
            Log.d(ROOM_TAG, "onLeave(" + code + ")");
        });

        room.setOnError((reason, message) -> {
            Log.d(ROOM_TAG, "onError(" + reason + ", " + message + ")");
        });
    }

    public void countRep() {
        try {
            _room.send("rep");
        } catch (Exception e) {
            Log.w("CounterActivity.countRep", "publishing count failed", e);
        }
    }
}
