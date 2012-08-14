package com.kinnack.nthings.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.kinnack.nthings.R;
import com.kinnack.nthings.activity.CounterActivity;
import com.kinnack.nthings.activity.WorkoutActions;
import com.kinnack.nthings.controller.FullWorkoutController;
import com.kinnack.nthings.controller.PushupFullWorkoutController;
import com.kinnack.nthings.controller.PushupWorkoutController;
import com.kinnack.nthings.controller.SitupFullWorkoutController;
import com.kinnack.nthings.controller.SitupWorkoutController;
import com.kinnack.nthings.controller.WorkoutController;
import com.kinnack.nthings.helper.CounterActivityManager;
import com.kinnack.nthings.model.DayAndWeek;
import com.kinnack.nthings.model.LevelSelectionViewAdapter;
import com.kinnack.nthings.model.SetTitleAdapter;
import com.kinnack.nthings.model.Workout.Type;
import com.kinnack.nthings.model.WorkoutSelectionViewAdapter;
import com.kinnack.nthings.model.level.Level;
import com.viewpagerindicator.TitlePageIndicator;

public class ExcerciseSettingsFragment extends SherlockFragment {
    
    private static final String WORKOUT_TYPE = "workout-type";
    public static final String PREFS = "prefs_config";
    
    private WorkoutController workoutController;
    private FullWorkoutController fullWorkoutController;
    private Editor prefEditor;
    private Type type;
    private View view;
    private WorkoutActions actions;
	private MenuItem _levelMenuItem;
	private MenuItem _rankMenuItem;
    
    public static ExcerciseSettingsFragment newInstance(Type workoutType_, WorkoutActions actions_) {
        ExcerciseSettingsFragment fragment = new ExcerciseSettingsFragment();
        fragment.actions = actions_;
        Bundle args = new Bundle();
        args.putString(WORKOUT_TYPE, workoutType_.toString());
        fragment.setArguments(args);
        
        switch (workoutType_) {
        case PUSHUP:
        	fragment.fullWorkoutController = new PushupFullWorkoutController(actions_);
        	break;
        case SITUP:
        	fragment.fullWorkoutController = new SitupFullWorkoutController(actions_);
        	break;
        }
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setHasOptionsMenu(true);
        if (savedInstanceState_ == null) savedInstanceState_= getArguments();
        
        type = Type.valueOf(savedInstanceState_.getString(WORKOUT_TYPE));
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater_, ViewGroup container_, Bundle savedInstanceState_) {
    	if (view != null) return view;
        view = inflater_.inflate(R.layout.excersice_settings_level, container_, false);
        final Button startButton = (Button) view.findViewById(R.id.startButton);
        startButton.setOnClickListener(fullWorkoutController);
        
        ViewPager pager = (ViewPager)view.findViewById(R.id.pager);
        
        fullWorkoutController.loadHistory(getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE));
        pager.setAdapter(fullWorkoutController.getPagerAdapter(getFragmentManager()));
      
        
        TitlePageIndicator titleIndicator = (TitlePageIndicator)view.findViewById(R.id.setTitles);
        titleIndicator.setViewPager(pager);
        titleIndicator.setOnPageChangeListener(fullWorkoutController);
        
        //setDayWeekSelectorOnItemClick();
        //setLevelSelectorOnItemSelect();
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        CounterActivityManager counterActivityManager = new CounterActivityManager(PreferenceManager.getDefaultSharedPreferences(getActivity()), getActivity());
        fullWorkoutController.setCounterActivityManager(counterActivityManager);
        fullWorkoutController.loadHistory(getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE));
        
        //listDayWeekOptions();
        //loadLevelOptions();ä
        //configureMainView();
        
        
        
        
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu_, MenuInflater inflater_) {
    	_levelMenuItem = menu_.findItem(R.id.levelMenuItem);
    	_rankMenuItem = menu_.findItem(R.id.rankMenuItem);
		
    }
    
   @Override
	public void onPrepareOptionsMenu(Menu menu_) {
	   //TODO set current level and rank
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item_) {
    	
    	int levelIndex = -1;
    	int rankIndex = -1;
    	switch(item_.getItemId()){
	    	case R.id.levelEasy: 
	    		_levelMenuItem.setTitle("I");
	    		break;
	    	case R.id.levelMedium:
	    		_levelMenuItem.setTitle("II");
	    		levelIndex = 1;
	    		break;
	    	case R.id.levelHard:
	    		_levelMenuItem.setTitle("III");
	    		levelIndex = 2;
	    		break;
	    		
	    	case R.id.rankPrivate:
	    		rankIndex = 1;
	    		break;
	    	case R.id.rankCorporal:
	    		rankIndex = 2;
	    		break;
	    	case R.id.rankSergant:
	    		rankIndex = 3;
	    		break;
	    	case R.id.rankMsgt:
	    		rankIndex = 4;
	    		break;
	    	case R.id.rankLt:
	    		rankIndex = 5;
	    		break;
	    	case R.id.rankCaptain:
	    		rankIndex = 6;
	    		break;
	    		
	    	default:
	    		return false;
    	}
    	if (levelIndex >= 0) {
    		Level level = LevelSelectionViewAdapter.getLevelByPosition(levelIndex);
    		fullWorkoutController.setCurrentLevel(level);
    	} else if (rankIndex >= 0) {
    		_rankMenuItem.setIcon(item_.getIcon());
    		Log.d("dgmt!onOptionsItemSelected","week changed to position "+rankIndex);
    		fullWorkoutController.setWeek(rankIndex);
    	}
    	return true;
    }
   
    private void configureMainView() {
        ((Button)view.findViewById(R.id.ActivityButton)).setEnabled(true);
        ((Button)view.findViewById(R.id.FinalButton)).setEnabled(false);
        
        Spinner levelSelector = (Spinner)view.findViewById(R.id.levelSelector);
        
        levelSelector.setEnabled(true);
        view.findViewById(R.id.dayWeekSelector).setEnabled(true);
        
        String value = "";
        if (workoutController.isTest()) {
            view.findViewById(R.id.dayWeekSelector).setEnabled(false);
            value = "TEST";
        } else if (workoutController.isFinal()) {
            view.findViewById(R.id.dayWeekSelector).setEnabled(false);
            levelSelector.setEnabled(false);
            value = "FINAL";
            ((Button)view.findViewById(R.id.ActivityButton)).setEnabled(false);
        } else {
            value = workoutController.getLevelForDisplay();
        }
        
        
        
        dayWeekOrLevelChanged();
        
        
        if (workoutController.isFinalUnlocked()) ((Button)view.findViewById(R.id.FinalButton)).setEnabled(true);
    }
    
    private void setDayWeekSelectorOnItemClick() {
        ((Spinner)view.findViewById(R.id.dayWeekSelector)).setOnItemSelectedListener(new OnItemSelectedListener() {
            private int previousPosition = -1;
            @Override
            public void onItemSelected(AdapterView<?> parent_, View view_, int position_, long id_) {
                Log.d("dgmt!dayWeekSelectorItemSelect","day and week changed to position "+position_);
                DayAndWeek dayAndWeek = WorkoutSelectionViewAdapter.getDayAndWeekByPosition(position_);
                boolean dayOrWeekChanged = !dayAndWeek.equals(workoutController.getDayAndWeek());
                if (dayAndWeek.wasFound() && !workoutController.isTest() && dayOrWeekChanged ) {
                    Log.d("dgmt!dayWeekSelectorItemSelect","day and week has changed");
                    workoutController.setDayAndWeek(dayAndWeek);
                    dayWeekOrLevelChanged();
                }
                previousPosition = position_;
                
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0_) {
                // TODO Auto-generated method stub
                
            }
        });
        
    }
    
    private void setLevelSelectorOnItemSelect(){
        ((Spinner)view.findViewById(R.id.levelSelector)).setOnItemSelectedListener(new OnItemSelectedListener() {

            
           
            @Override
            public void onItemSelected(AdapterView<?> parent_, View view_, int position_, long id_) {
                Log.d("dgmt!levelSelectorItemSelect","Level changed to position "+position_);
                Level level = LevelSelectionViewAdapter.getLevelByPosition(position_);
               
                boolean levelChanged = !level.equals(workoutController.getCurrentLevel());
                Log.d("dgmt!levelSelectorItemSelect","Level changed?"+levelChanged+". current="+workoutController.getCurrentLevel()+"] selected="+level);
                if(position_ != 3 && workoutController.setCurrentLevel(level)) {
                    Log.d("dgmt!levelSelectorItemSelect", "Level has actually changed");
                    view.findViewById(R.id.dayWeekSelector).setEnabled(true);
                }
                if (levelChanged) {dayWeekOrLevelChanged();}
                
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0_) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    
   
    
    
    public void listDayWeekOptions() {
        Spinner dayWeekSelector = (Spinner)view.findViewById(R.id.dayWeekSelector);
        if (dayWeekSelector.getAdapter() !=  null) { return; }
        WorkoutSelectionViewAdapter listAdapter = new WorkoutSelectionViewAdapter(getActivity(), workoutController.isFinal());
        dayWeekSelector.setAdapter(listAdapter);
        Log.d("dgmt:listDayWeekOptions","Getting position for dayWeek with week="+workoutController.getWeek()+" and day="+workoutController.getDay());
        dayWeekSelector.setSelection(listAdapter.getPositionForWeekDay(workoutController.getWeek(), workoutController.getDay()));
    }
    
    protected void dayWeekOrLevelChanged() {
        String count = (String) getResources().getText(R.string.count_for_test_msg);
        if (!workoutController.shouldDisplayDayAsTest(null)) { count = workoutController.totalCountLeft(null)+""; }
        ((TextView)view.findViewById(R.id.count_for_settings)).setText("Drop and Give Me "+count+"!");
    }
    
    public void loadLevelOptions() {
        Spinner levelSelector = (Spinner)view.findViewById(R.id.levelSelector);
        boolean showTest = workoutController.shouldDisplayDayAsTest(null);
        if (levelSelector.getAdapter() == null){
            LevelSelectionViewAdapter viewAdapter = new LevelSelectionViewAdapter(getActivity(), showTest);
            levelSelector.setAdapter(viewAdapter);
        }
        levelSelector.setSelection(showTest ? 3 :LevelSelectionViewAdapter.getPositionForLevel(workoutController.getCurrentLevel()));
    }
    
    

    
    public String getLabel() { return type.getLabel(); }
    public WorkoutController getWorkoutController() { return workoutController; }

	public FullWorkoutController getFullWorkoutController() {
		return fullWorkoutController;
	}
    

}
