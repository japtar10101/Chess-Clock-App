/**
 * Package of menus
 */
package com.app.chessclock.menus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.app.chessclock.Global;
import com.app.chessclock.R;

/**
 * Sets up the menu for options
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity {
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
     * Called when the activity is first created.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    // First, setup the UI
		this.addPreferencesFromResource(R.layout.options);
	}
    
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onResume() {
    	// Do whatever is in the super class first
        super.onResume();
        this.recallSettings();
    }
    
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {
    	// Do whatever is in the super class first
        super.onPause();
        // TODO: save the results
        this.recallSettings();
    }
    
    private void recallSettings() {
    	// Retrieve the preference page
		final SharedPreferences settings =
			PreferenceManager.getDefaultSharedPreferences(this);
    	
		// Update the options
    	Global.OPTIONS.recallSettings(settings);
    }
}
