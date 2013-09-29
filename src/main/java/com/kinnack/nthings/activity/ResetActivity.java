package com.kinnack.nthings.activity;

import java.io.File;

import com.kinnack.nthings.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;

public class ResetActivity extends Activity {
    private static final String PUBLIC_FOLDER_PATH=Environment.getExternalStorageDirectory()+"/nhundredthings/";
    private static final String PUBLIC_FILE_PATH=PUBLIC_FOLDER_PATH+"/prefs_config.xml";
    private static final String PRIVATE_FILE_PATH = "/data/data/"+Home.class.getPackage().getName()+"/shared_prefs/prefs_config.xml";
    
    public static final String RESET = "com.kinnack.nthings.reset";
    
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
       super.onCreate(savedInstanceState_);
       setContentView(R.layout.reset);
       registerForContextMenu(findViewById(R.id.resetLayout));
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu_, View v_, ContextMenuInfo menuInfo_) {
        super.onCreateContextMenu(menu_, v_, menuInfo_);
        getMenuInflater().inflate(R.menu.reset_context, menu_);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item_) {
        SharedPreferences prefs = getSharedPreferences("prefs_config", Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
        new File(PUBLIC_FILE_PATH).delete();
        Intent intent = new Intent();
        intent.putExtra(RESET, true);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }
}
