package com.kinnack.nthings.fragments;

import android.content.Context;
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
    
    public static ExcerciseSettingsFragment newInstance(Type workoutType_) {
        ExcerciseSettingsFragment fragment = new ExcerciseSettingsFragment();
        
        Bundle args = new Bundle();
        args.putString(WORKOUT_TYPE, workoutType_.toString());
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setHasOptionsMenu(true);
        if (savedInstanceState_ == null) savedInstanceState_= getArguments();
        
        type = Type.valueOf(savedInstanceState_.getString(WORKOUT_TYPE));
        
        switch (type) {
            case PUSHUP:
                fullWorkoutController = new PushupFullWorkoutController();
                break;
            case SITUP:
                fullWorkoutController = new SitupFullWorkoutController();
                break;
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater_, ViewGroup container_, Bundle savedInstanceState_) {
    	if (view != null) return view;
        view = inflater_.inflate(R.layout.excersice_settings_level, container_, false);
        final Button startButton = (Button) view.findViewById(R.id.startButton);
        
        ViewPager pager = (ViewPager)view.findViewById(R.id.pager);
        
        fullWorkoutController.loadHistory(getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE));
        pager.setAdapter(fullWorkoutController.getPagerAdapter(getFragmentManager()));
      
        
        TitlePageIndicator titleIndicator = (TitlePageIndicator)view.findViewById(R.id.setTitles);
        titleIndicator.setViewPager(pager);
        titleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	
        	@Override
        	public void onPageSelected(int pageNumber_) {
        		fullWorkoutController.setCurrentSet(pageNumber_);
        		startButton.setOnClickListener(fullWorkoutController.getCurrentStartListener());
        	}
        	
        	@Override public void onPageScrolled(int arg0_, float arg1_, int arg2_) {}
        	@Override public void onPageScrollStateChanged(int arg0_) {}
        });
        
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
    	MenuItem levelMenuItem = menu_.findItem(R.id.levelMenuItem);
		
    }
    
   @Override
	public void onPrepareOptionsMenu(Menu menu_) {
		MenuItem levelMenuItem = menu_.findItem(R.id.levelMenuItem);
		levelMenuItem.setTitle("III");
		MenuItem rankMenuItem = menu_.findItem(R.id.rankMenuItem);
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
    

}
