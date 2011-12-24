package com.kinnack.nthings.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kinnack.nthings.R.id.*;
import com.kinnack.nthings.R;

import android.content.Context;
import android.widget.SimpleAdapter;

public class HistoricViewAdapter extends SimpleAdapter {
    public static final String WEEK="week";
    public static final String DAY="day";
    public static final String LEVEL="level";
    public static final String COUNTS="counts";
    public static final String AVG_TIME="avgTime";
    public static final String DATE="date";
    public static final String FREQUENCY="frequency";
    
    public HistoricViewAdapter(Context context_, History history_) {
        super(context_,historyToMapList(history_),R.layout.history_item,
                new String[]{WEEK,DAY,LEVEL,COUNTS,DATE},
                new int[]{HomeCurrentWeek,HomeCurrentDay,HomeCurrentLevel,counts,completionDate});
    }
    
    private static List<Map<String, ?>> historyToMapList(History history_) {
        List<Map<String,?>> historyAsMapList = new ArrayList<Map<String,?>>();
        for(Logg log : history_.getLogs()) {
            Map<String,String> historyAsMap = new HashMap<String, String>();
            historyAsMap.put(WEEK, ""+log.getWeek());
            historyAsMap.put(DAY, ""+log.getDay());
            historyAsMap.put(LEVEL, history_.getCurrentLevel().getLabel());
            historyAsMap.put(COUNTS, join(log.getCounts(),","));
            historyAsMap.put(DATE, "mm/dd/yyyy" );
            historyAsMap.put(AVG_TIME, log.getAverageMillisPerPushup()+"ms");
            historyAsMap.put(FREQUENCY, log.getAveragePushupFrequency()+"pu/s");
            historyAsMapList.add(historyAsMap);
        }
        return historyAsMapList;
    }
    
    private static String join(Collection<?> items, String separator_) {
        StringBuilder builder = new StringBuilder();
        for(Object item : items) {
            builder.append(item.toString()).append(separator_);
        }
        builder.delete(builder.length()-separator_.length(), builder.length()-1);
        return builder.toString();
    }
}
