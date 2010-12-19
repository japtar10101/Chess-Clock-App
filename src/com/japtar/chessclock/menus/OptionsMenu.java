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
import com.japtar.chessclock.enums.DelayMode;
import com.japtar.chessclock.enums.TimerCondition;
import com.japtar.chessclock.models.OptionsModel;

/**
 * Sets up the menu for options
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity implements
		Preference.OnPreferenceChangeListener {
	/** The key value for all time-related stuff */
	public static final String KEY_TIME_GROUP = "timeGroup";
	/** Key to get advanced options button */
	public static final String KEY_ADVANCED_OPTIONS = "advancedOptions";
	/** Key to get delay mode list */
	public static final String KEY_DELAY_MODE = "delayMode";
	
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
		
		// Set the list preference's listener to this
		this.findPreference(KEY_DELAY_MODE).
				setOnPreferenceChangeListener(this);
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
    
	/**
	 * Update the delay mode description
	 * @see android.preference.Preference.OnPreferenceChangeListener#onPreferenceChange(android.preference.Preference, java.lang.Object)
	 */
	@Override
	public boolean onPreferenceChange(final Preference preference,
			final Object newValue) {
		// Check if the newValue is a String
		if(newValue instanceof String) {
			
			// Convert to string
			final String delayMode = (String) newValue;
			
			// Get the delay Preference
			final Preference editPref = this.findPreference(
					OptionsModel.KEY_WHITE_DELAY_TIME);
			
			// Update the delay preference text
			if(delayMode.equals(DelayMode.STRING_BASIC)) {
				editPref.setEnabled(true);
				editPref.setTitle(R.string.basicDelayPref);
				editPref.setSummary(R.string.basicDelaySummaryPref);
			} else if((delayMode.equals(DelayMode.STRING_FISCHER)) ||
					(delayMode.equals(DelayMode.STRING_FISCHER_AFTER)))  {
				editPref.setEnabled(true);
				editPref.setTitle(R.string.fischerPref);
				editPref.setSummary(R.string.fischerSummaryPref);
			} else if(delayMode.equals(DelayMode.STRING_BRONSTEIN)) {
				editPref.setEnabled(true);
				editPref.setTitle(R.string.bronsteinPref);
				editPref.setSummary(R.string.bronsteinSummaryPref);				
			} else {
				editPref.setEnabled(false);
				editPref.setTitle(R.string.basicDelayPref);
				editPref.setSummary(R.string.disabledDelaySummaryPref);
			}
		}
		
		// Allow changes
		return true;
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
    	Preference editPref = this.findPreference(KEY_TIME_GROUP);
		editPref.setEnabled(enabled);
    	
		// Update the time limit summary
    	editPref = this.findPreference(
				OptionsModel.KEY_WHITE_TIME_LIMIT);
		if(enabled) {
			editPref.setSummary(R.string.gameDurationSummaryPref);
		} else {
			editPref.setSummary(R.string.disabledSummaryPref);
		}
		
		// Update the delay time summary
		editPref = this.findPreference(
				OptionsModel.KEY_WHITE_DELAY_TIME);
		if(enabled) {
			switch(Global.OPTIONS.delayMode) {
				case DelayMode.BASIC:
					editPref.setEnabled(true);
					editPref.setTitle(R.string.basicDelayPref);
					editPref.setSummary(R.string.basicDelaySummaryPref);
					break;
				case DelayMode.FISCHER:
				case DelayMode.FISCHER_AFTER:
					editPref.setEnabled(true);
					editPref.setTitle(R.string.fischerPref);
					editPref.setSummary(R.string.fischerSummaryPref);
					break;
				case DelayMode.BRONSTEIN:
					editPref.setEnabled(true);
					editPref.setTitle(R.string.bronsteinPref);
					editPref.setSummary(R.string.bronsteinSummaryPref);
					break;
				default:
					editPref.setEnabled(false);
					editPref.setTitle(R.string.basicDelayPref);
					editPref.setSummary(R.string.disabledDelaySummaryPref);
					break;
			}
		} else {
			editPref.setSummary(R.string.disabledSummaryPref);
		}
		
		// Update advanced options' summary
    	editPref = this.findPreference(KEY_ADVANCED_OPTIONS);
		if(enabled) {
			editPref.setSummary(R.string.advancedPrefSummary);
		} else {
			editPref.setSummary(R.string.disabledSummaryPref);
		}
    }
}
