package com.kinnack.nthings;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.History.Log;

public class ProgressChart {
    final static int DARK_COLOR = Color.argb(255, 51, 51, 51);
    final static int APPROVED_GREEN = Color.rgb(0, 204, 0);
    
    public Intent progressChart(History history_, Context context_) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
        seriesRenderer.setColor(APPROVED_GREEN);
        renderer.addSeriesRenderer(seriesRenderer);
        renderer.setOrientation(Orientation.HORIZONTAL);
        
        
        //settings
        renderer.setChartTitle(history_.getType()+" Progress");
        renderer.setXTitle("Workout");
        renderer.setYTitle("Count");
        renderer.setXAxisMin(1);
        renderer.setXAxisMax(18);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(150);
        renderer.setAxesColor(DARK_COLOR);
        renderer.setLabelsColor(DARK_COLOR);
        
        renderer.setXLabels(18);
        renderer.setYLabels(15);
        renderer.setShowLegend(false);
        renderer.setDisplayChartValues(true);
        
        XYMultipleSeriesDataset data = new XYMultipleSeriesDataset();
        CategorySeries series = new CategorySeries("Progress");
        List<Log> logs = history_.getLogs();
        for(int i = 0,len = logs.size(); i < len; i++) {
            Log log = logs.get(i);
            series.add(log.getTotalCount());
        }
        data.addSeries(series.toXYSeries());
        
        return ChartFactory.getBarChartIntent(context_, data, renderer, Type.DEFAULT);
    }
    
    
}
