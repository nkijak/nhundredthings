package com.kinnack.nthings.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kinnack.nthings.model.ExerciseSet;

public class SetOverviewChart extends View {
	public SetOverviewChart(Context context_, AttributeSet attrs_, int defStyle_) {
		super(context_, attrs_, defStyle_);
		setup();
	}
	
	public SetOverviewChart(Context context_, AttributeSet attrs_) {
		super(context_, attrs_);
		setup();
	}
	
	private void setup() {
		setMinimumHeight(150);
		setMinimumWidth(300);
		_paint = new Paint();
		_paint.setColor(_color);
		_counts = new ArrayList<Integer>();
	}

	private ExerciseSet _exercseSet;
	private int _color = 0xff397f00;
	private Paint _paint;
	private List<Integer> _counts;
	private int _maxCountForSet;
	private int _maxCount = -1;
	
	
	public void setExercseSet(ExerciseSet exercseSet_) {
		_exercseSet = exercseSet_;
		
		_counts.clear();
		_maxCountForSet = Integer.MIN_VALUE;
		for(int count : _exercseSet.getCounts()) {
		
			if (count > _maxCountForSet) _maxCountForSet = count;
			_counts.add(count);
		}
		
		invalidate();
	}

	public int getColor() {
		return _color;
	}

	public void setColor(int color_) {
		_color = color_;
		
		_paint.setColor(_color);
	}

	public void setMaxX(int maxX) {
		_maxCount = maxX;
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec_, int heightMeasureSpec_) {
		setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
	}
	
	@Override
	protected void onDraw(Canvas canvas_) {
		
		_paint.setStrokeWidth(2);
		
		
		float baseY = (float)Math.floor(getHeight()*0.95);
		canvas_.drawLine(5, baseY, getWidth()-5,baseY, _paint);
		
		if (_exercseSet == null) return;
		
		int x = (int)Math.floor(getWidth()*0.05);
		int gap = (int)Math.floor(getWidth()*0.9/_counts.size());
		_paint.setStrokeWidth(20);
		float heightPercent = 100.0f;
		Log.d("SetOverviewChart", "Drawing chart...");
		for (Integer count : _counts) {
			heightPercent = 1.0f - count*1.0f/_maxCount;
			Log.d("SetOverviewChart", "Drawing "+count+" at "+heightPercent+"% of "+_maxCount+" from "+baseY+" to "+(getHeight()*heightPercent));
			canvas_.drawLine(x, baseY, x, (getHeight()*heightPercent), _paint);
			x += gap;
		}
		
	}

}
