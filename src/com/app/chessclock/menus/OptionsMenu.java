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
import com.app.chessclock.gui.TimerPreference;

/**
 * Sets up the menu for options
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == TimerPreference ==
	/** "Time Limit" picker */
	private TimerPreference mTimeLimit = null;
	/** "Delay Time" picker */
	private TimerPreference mDelayTime = null;
	
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
		
		// Grab the TimePickers
		mTimeLimit = (TimerPreference) this.findPreference("prefTimeLimit");
		mDelayTime = (TimerPreference) this.findPreference("prefTimeDelay");
		
		// Update the TimePickers to current settings
		mTimeLimit.setModel(Global.OPTIONS.savedTimeLimit);
		mDelayTime.setModel(Global.OPTIONS.savedDelayTime);
	}
    
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {
    	// Do whatever is in the super class first
        super.onPause();

        // Retrieve the preference page
		final SharedPreferences settings =
			PreferenceManager.getDefaultSharedPreferences(this);
		
		// Save any un-saved options
    	Global.OPTIONS.saveSettings(settings);
    	
		// Update the options
    	Global.OPTIONS.recallSettings(settings);
    }
}
