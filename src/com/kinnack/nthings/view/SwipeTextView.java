package com.kinnack.nthings.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.TextView;

public class SwipeTextView extends TextView {
    private static final float DISTANCE_DIP = 16.0f;
    private static final float PATH_DIP = 80.0f;
    private int minScaledVelocity;
    
    private GestureDetector gestureDetector;
    
    public SwipeTextView(Context context_) {
        super(context_);
        init();
    }
    
    public SwipeTextView(Context context_, AttributeSet attributeSet_) {
        super(context_,attributeSet_);
        init();
    }
    
    private void init() {
        gestureDetector =new GestureDetector(new SimpleOnGestureListener() {
            public boolean onFling(MotionEvent e1_, MotionEvent e2_, float velocityX_, float velocityY_) {
                int scaledDistance;
                int scaledPath;
                         
                // get distance between points of the fling
                double vertical = Math.abs( e1_.getY() - e2_.getY() );
                double horizontal = Math.abs( e1_.getX() - e2_.getX() );
                Log.d("SETCOUNT", "vertical fling = "+vertical+", horizontal = "+horizontal);
                // convert dip measurements to pixels
                final float scale = getResources().getDisplayMetrics().density;
                scaledDistance = (int) ( DISTANCE_DIP * scale + 0.5f );
                scaledPath = (int) ( PATH_DIP * scale + 0.5f );        
                    
                // test vertical distance, make sure it's a swipe
                if ( vertical > PATH_DIP ) {
                        Log.d("SETCOUNT",vertical+" <= "+PATH_DIP+" returning false for fling");
                        return false;
                        
                }
                // test horizontal distance and velocity
                else if ( horizontal > DISTANCE_DIP && Math.abs(velocityX_) > minScaledVelocity ) {
                    // right to left swipe
                    if (velocityX_< 0 ) {
                        Log.d("SETCOUNT", "Swipe <- @"+velocityX_);
                        //do stuff
                    }
                    return true;
                }
                Log.d("SETCOUNT",horizontal+"<="+DISTANCE_DIP+" or "+Math.abs(velocityX_)+" <= "+minScaledVelocity+" so not registering swipe");
                 return false;
            }
        });
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event_) {
        Log.d("SwipeTextView.onTouchEvent",event_.toString());
        return gestureDetector.onTouchEvent(event_);
    }
    

}
