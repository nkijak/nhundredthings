package com.kinnack.nthings;

import com.kinnack.nthings.R;
import com.kinnack.nthings.model.ExerciseSet;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity {
    public static final String INIT_COUNT_KEY = "com.kinnack.nthings.init_count";
    public static final String SHOW_DONE = "com.kinnack.nthings.show_done";
    public static final String MAX_COUNT = "com.kinnack.nthing.max_count";
    private int count = 0;
    private int increment = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = getIntent().getExtras().getInt(INIT_COUNT_KEY);
        boolean showDone = getIntent().getExtras().getBoolean(SHOW_DONE);
        if (!showDone) { increment = -1; }
        if (showDone) { count = 0; }
        setContentView(R.layout.counter);
        TextView totalCount = (TextView) findViewById(R.id.TotalCount);
        totalCount.setText(""+count);
        if (showDone) {
            Button done = (Button) findViewById(R.id.Done);
            done.setVisibility(View.VISIBLE);
        }
    }
    
    public void count(View target) {
        TextView totalCount = (TextView) findViewById(R.id.TotalCount);
        totalCount.setText(""+(count+=increment));
        if ((increment  == -1) && (count == 0)) {
            setResult(RESULT_OK); 
            finish();
        }
    }
    
    public void done(View target) {
        setResult(RESULT_OK);
        getIntent().getExtras().putInt(MAX_COUNT, count);
        Log.d("nthings:CounterActivity","Set "+MAX_COUNT+" to "+count);
        finish();
    }
}
