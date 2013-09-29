package com.kinnack.nthings.helper;

import android.text.Editable;
import android.text.TextWatcher;

public class RangedIntTextWatcher implements TextWatcher{
    private int start;
    private int end;
    private boolean isValid;
    
    public RangedIntTextWatcher(int start_, int end_) {
        start = start_;
        end = end_;
    }
    
    @Override
    public void afterTextChanged(Editable s_) {
        int value = start;
        isValid = false;
        try {
            value = Integer.parseInt(s_.toString());
            if (value < start) {
                value = 0;
            } else if(value > end) {
                value = end;
            }
            
        } catch(NumberFormatException nfe) {
        }
        if (!String.valueOf(value).equals(s_.toString())) {
            s_.replace(0, s_.length(), value+"", 0, (value+"").length());
        }
        isValid = true;
        
    }

    @Override
    public void beforeTextChanged(CharSequence s_, int start_, int count_, int after_) {
        // don't care
    }

    @Override
    public void onTextChanged(CharSequence s_, int start_, int before_, int count_) {
        // dont' care
    }

    /**
     * @return the isValid
     */
    public boolean isValid() {
        return isValid;
    }

}
