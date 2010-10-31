package com.app.chessclock;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	// Do whatever is in the super class first
        super.onCreate(savedInstanceState);
        
        // Update all constant variables
        this.getWindowManager().getDefaultDisplay().getMetrics(Global.DISPLAY);
        Global.SAVED_STATE.putAll(savedInstanceState);
        Global.OPTIONS.loadOptions(savedInstanceState);
        
        // Set the default layout to main
        Global.TIMER_MENU.setupLayout(this);
    }
} 
