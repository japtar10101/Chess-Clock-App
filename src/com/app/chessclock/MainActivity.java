package com.app.chessclock;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends Activity {
	public static final DisplayMetrics DISPLAY = new DisplayMetrics();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	// Do whatever is in the super class first
        super.onCreate(savedInstanceState);
        
        // Update the Display Manager
        getWindowManager().getDefaultDisplay().getMetrics(DISPLAY);
        
        // Set the default layout to main
        setContentView(R.layout.main);
    }
} 
