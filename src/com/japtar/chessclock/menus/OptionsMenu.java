/**
 * Package of menus
 */
package com.japtar.chessclock.menus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.japtar.chessclock.R;
import com.japtar.chessclock.Global;
import com.japtar.chessclock.enums.TimerCondition;
import com.japtar.chessclock.models.OptionsModel;

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
		
		// Disable and update text, if necessary
		this.updatePreferenceEnabled();
	}
    
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {
    	// Do whatever is in the super class first
        super.onPause();
        this.recallSettings();
    }
    
    /**
     * Called when the activity resumes.
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {
    	// Do whatever is in the super class first
        super.onResume();
        // Disable and update text, if necessary
		this.updatePreferenceEnabled();
    }
    
    /* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
    /**
     * Recalls the preferences values into Global.Options
     */
    private void recallSettings() {
    	// Retrieve the preference page
		final SharedPreferences settings =
			PreferenceManager.getDefaultSharedPreferences(this);
    	
		// Update the options
    	Global.OPTIONS.recallSettings(settings);
    }
    
    private void updatePreferenceEnabled() {
    	// Figure out whether to enable or disable the preferences
    	final boolean enabled;
    	switch(Global.GAME_STATE.timerCondition) {
			case TimerCondition.RUNNING:
			case TimerCondition.PAUSE:
				enabled = false;
				break;
			default:
				enabled = true;
				break;
		}
		
    	// Enable/disable the time limit
		Preference editPref = this.findPreference(
				OptionsModel.KEY_TIME_LIMIT);
		editPref.setEnabled(enabled);
    	
		// Update the time limit summary
		if(enabled) {
			editPref.setSummary(R.string.gameDurationSummaryPref);
		} else {
			editPref.setSummary(R.string.disabledSummaryPref);
		}
		
		// Update the delay time summary
		editPref = this.findPreference(
				OptionsModel.KEY_DELAY_TIME);
		editPref.setEnabled(enabled);
		
		// Enable/disable the delay time
		if(enabled) {
			editPref.setSummary(R.string.delayDurationSummaryPref);
		} else {
			editPref.setSummary(R.string.disabledSummaryPref);
		}
    }
}
