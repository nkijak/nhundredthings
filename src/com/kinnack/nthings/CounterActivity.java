package com.kinnack.nthings;

import com.kinnack.nthings.R;
import com.kinnack.nthings.model.ExerciseSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CounterActivity extends Activity {
    public static final String INIT_COUNT_KEY = "com.kinnack.nthings.init_count";
    private int count = 0;
    private int increment = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = getIntent().getExtras().getInt(INIT_COUNT_KEY);
        if (count > 0) { increment = -1; }
        if (count == ExerciseSet.TO_EXAUSTION) { count = 0; }
        setContentView(R.layout.counter);
        TextView totalCount = (TextView) findViewById(R.id.TotalCount);
        totalCount.setText(""+count);
        
    }
    
    public void count(View target) {
        TextView totalCount = (TextView) findViewById(R.id.TotalCount);
        totalCount.setText(""+(count+=increment));
        if ((increment  == -1) && (count == 0)) {
            setResult(RESULT_OK); 
            finish();
        }
    }
}
