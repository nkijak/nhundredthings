package com.kinnack.nthings;

import java.util.Collection;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import com.kinnack.nthings.model.History;
import com.kinnack.nthings.model.History.Log;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class ProgressChart {
    public Intent progressChart(History history_, Context context_) {
        int darkColor = Color.argb(255, 51, 51, 51);

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
        seriesRenderer.setColor(Color.GREEN);
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
        renderer.setAxesColor(darkColor);
        renderer.setLabelsColor(darkColor);
        
        renderer.setXLabels(18);
        renderer.setYLabels(15);
        renderer.setShowLegend(false);
        renderer.setDisplayChartValues(true);
        
        XYMultipleSeriesDataset data = new XYMultipleSeriesDataset();
        CategorySeries series = new CategorySeries("Progress");
        List<Log> logs = history_.getLogs();
        for(int i = 0,len = logs.size(); i < len; i++) {
            Log log = logs.get(i);
            series.add(sum(log.getCounts()));
        }
        data.addSeries(series.toXYSeries());
        
        return ChartFactory.getBarChartIntent(context_, data, renderer, Type.DEFAULT);
    }
    
    protected long sum(Collection<Integer> values_) {
        long sum = 0;
        for (Integer value : values_) {
            sum += value;
        }
        return sum;
    }
}
