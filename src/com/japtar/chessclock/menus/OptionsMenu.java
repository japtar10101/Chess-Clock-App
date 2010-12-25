/**
 * Package of menus
 */
package com.japtar.chessclock.menus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.japtar.chessclock.Global;
import com.japtar.chessclock.R;
import com.japtar.chessclock.enums.DelayMode;
import com.japtar.chessclock.enums.TimerCondition;
import com.japtar.chessclock.models.OptionsModel;

/**
 * Sets up the menu for options
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity implements
		Preference.OnPreferenceChangeListener {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	/** The key value for all time-related stuff */
	public static final String KEY_TIME_GROUP = "timeGroup";
	/** Key to get advanced options button */
	public static final String KEY_ADVANCED_OPTIONS = "advancedOptions";
	/** Key to get delay mode list */
	public static final String KEY_DELAY_MODE = "delayMode";
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	
	/** TODO */
	private Preference mBlackTime = null;
	/** TODO */
	private Preference mBlackDelay = null;
	
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
		
		// Setup the member variables
		mBlackTime = this.findPreference(
				OptionsModel.KEY_BLACK_TIME_LIMIT);
		mBlackDelay = this.findPreference(
				OptionsModel.KEY_BLACK_DELAY_TIME);
		
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
		if(OptionsModel.KEY_DELAY_MODE.equals(preference.getKey())) {
			
			// Convert to string
			final String delayMode = (String) newValue;
			
			// Update the delay preference text	
			if(Global.OPTIONS.enableHandicap) {
				this.updateDelayTextWithHandicap(delayMode);
			} else {
				this.updateDelayTextNoHandicap(delayMode);
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
    
    /**
     * TODO: document
     */
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
		if(Global.OPTIONS.enableHandicap) {
			this.updateDelayTextWithHandicap(enabled);
		} else {
			this.updateDelayTextNoHandicap(enabled);
		}
		
		// Update advanced options' summary
    	editPref = this.findPreference(KEY_ADVANCED_OPTIONS);
		if(enabled) {
			editPref.setSummary(R.string.advancedPrefSummary);
		} else {
			editPref.setSummary(R.string.disabledSummaryPref);
		}
    }
    
	/**
	 * TODO: document
	 * @param delayMode
	 */
	private void updateDelayTextNoHandicap(final String delayMode) {
		// Get the black player's options
		Preference editPref = this.findPreference(
				OptionsModel.KEY_BLACK_TIME_LIMIT);
		
		// Don't show the black player's time options
		if(editPref != null) {
			this.getPreferenceScreen().removePreference(mBlackTime);
			this.getPreferenceScreen().removePreference(mBlackDelay);
		}
		
		// Get the delay Preference
		editPref = this.findPreference(
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
	
	/**
	 * TODO: document
	 * @param delayMode
	 */
	private void updateDelayTextWithHandicap(final String delayMode) {
		// Get the delay Preference
		final Preference whiteDelayPref = this.findPreference(
				OptionsModel.KEY_WHITE_DELAY_TIME);
		Preference blackDelayPref = this.findPreference(
				OptionsModel.KEY_BLACK_DELAY_TIME);
		
		// If the black player's options aren't visible, make it visible
		if(blackDelayPref == null) {
			this.getPreferenceScreen().addPreference(mBlackTime);
			this.getPreferenceScreen().addPreference(mBlackDelay);
			blackDelayPref = mBlackDelay;
		}
		
		// Update the delay preference text
		if(delayMode.equals(DelayMode.STRING_BASIC)) {
			whiteDelayPref.setEnabled(true);
			whiteDelayPref.setTitle(R.string.basicDelayPrefWhite);
			whiteDelayPref.setSummary(R.string.basicDelaySummaryPrefWhite);
			blackDelayPref.setEnabled(true);
			blackDelayPref.setTitle(R.string.basicDelayPrefBlack);
			blackDelayPref.setSummary(R.string.basicDelaySummaryPrefBlack);
		} else if((delayMode.equals(DelayMode.STRING_FISCHER)) ||
				(delayMode.equals(DelayMode.STRING_FISCHER_AFTER)))  {
			whiteDelayPref.setEnabled(true);
			whiteDelayPref.setTitle(R.string.fischerPrefWhite);
			whiteDelayPref.setSummary(R.string.fischerSummaryPrefWhite);
			blackDelayPref.setEnabled(true);
			blackDelayPref.setTitle(R.string.fischerPrefBlack);
			blackDelayPref.setSummary(R.string.fischerSummaryPrefBlack);
		} else if(delayMode.equals(DelayMode.STRING_BRONSTEIN)) {
			whiteDelayPref.setEnabled(true);
			whiteDelayPref.setTitle(R.string.bronsteinPrefWhite);
			whiteDelayPref.setSummary(R.string.bronsteinSummaryPrefWhite);				
			blackDelayPref.setEnabled(true);
			blackDelayPref.setTitle(R.string.bronsteinPrefBlack);
			blackDelayPref.setSummary(R.string.bronsteinSummaryPrefBlack);
		} else {
			whiteDelayPref.setEnabled(false);
			whiteDelayPref.setTitle(R.string.basicDelayPrefWhite);
			whiteDelayPref.setSummary(R.string.disabledDelaySummaryPref);
			blackDelayPref.setEnabled(false);
			blackDelayPref.setTitle(R.string.basicDelayPrefBlack);
			blackDelayPref.setSummary(R.string.disabledDelaySummaryPref);
		}
	}
	
	/**
	 * TODO: document
	 * @param enabled
	 */
	private void updateDelayTextNoHandicap(final boolean enabled) {
		// Get the black player's options
		Preference editPref = this.findPreference(
				OptionsModel.KEY_BLACK_TIME_LIMIT);
		
		// Don't show the black player's time options
		if(editPref != null) {
			this.getPreferenceScreen().removePreference(mBlackTime);
			this.getPreferenceScreen().removePreference(mBlackDelay);
		}
		
		// Update white player's delay text, based on game mode
		editPref = this.findPreference(
				OptionsModel.KEY_WHITE_DELAY_TIME);
		switch(Global.OPTIONS.delayMode) {
			case DelayMode.FISCHER:
			case DelayMode.FISCHER_AFTER:
				editPref.setTitle(R.string.fischerPref);
				editPref.setSummary(R.string.fischerSummaryPref);
				break;
			case DelayMode.BRONSTEIN:
				editPref.setTitle(R.string.bronsteinPref);
				editPref.setSummary(R.string.bronsteinSummaryPref);
				break;
			default:
				editPref.setTitle(R.string.basicDelayPref);
				editPref.setSummary(R.string.basicDelaySummaryPref);
				break;
		}
		
		// If disabled, indicate the reason 
		switch(Global.OPTIONS.delayMode) {
			case DelayMode.BASIC:
			case DelayMode.FISCHER:
			case DelayMode.FISCHER_AFTER:
			case DelayMode.BRONSTEIN:
				editPref.setEnabled(enabled);
				if(!enabled) {
					editPref.setSummary(R.string.disabledSummaryPref);
				}
				break;
			default:
				editPref.setEnabled(false);
				editPref.setSummary(R.string.disabledDelaySummaryPref);
				break;
		}
	}
	
	/**
	 * TODO: document
	 * @param enabled
	 */
	private void updateDelayTextWithHandicap(final boolean enabled) {
		// Get the delay Preference
		final Preference whiteDelayPref = this.findPreference(
				OptionsModel.KEY_WHITE_DELAY_TIME);
		Preference blackDelayPref = this.findPreference(
				OptionsModel.KEY_BLACK_DELAY_TIME);
		
		// If the black player's options aren't visible, make it visible
		if(blackDelayPref == null) {
			this.getPreferenceScreen().addPreference(mBlackTime);
			this.getPreferenceScreen().addPreference(mBlackDelay);
			blackDelayPref = mBlackDelay;
		}
				
		// Update white player's delay text, based on game mode
		switch(Global.OPTIONS.delayMode) {
			case DelayMode.FISCHER:
			case DelayMode.FISCHER_AFTER:
				whiteDelayPref.setTitle(R.string.fischerPrefWhite);
				whiteDelayPref.setSummary(R.string.fischerSummaryPrefWhite);
				blackDelayPref.setTitle(R.string.fischerPrefBlack);
				blackDelayPref.setSummary(R.string.fischerSummaryPrefBlack);
				break;
			case DelayMode.BRONSTEIN:
				whiteDelayPref.setTitle(R.string.bronsteinPrefWhite);
				whiteDelayPref.setSummary(R.string.bronsteinSummaryPrefWhite);
				blackDelayPref.setTitle(R.string.bronsteinPrefBlack);
				blackDelayPref.setSummary(R.string.bronsteinSummaryPrefBlack);
				break;
			default:
				whiteDelayPref.setTitle(R.string.basicDelayPrefWhite);
				whiteDelayPref.setSummary(R.string.basicDelaySummaryPrefWhite);
				blackDelayPref.setTitle(R.string.basicDelayPrefBlack);
				blackDelayPref.setSummary(R.string.basicDelaySummaryPrefBlack);
				break;
		}
		
		// If disabled, indicate the reason 
		switch(Global.OPTIONS.delayMode) {
			case DelayMode.BASIC:
			case DelayMode.FISCHER:
			case DelayMode.FISCHER_AFTER:
			case DelayMode.BRONSTEIN:
				whiteDelayPref.setEnabled(enabled);
				blackDelayPref.setEnabled(enabled);
				if(!enabled) {
					whiteDelayPref.setSummary(R.string.disabledSummaryPref);
					blackDelayPref.setSummary(R.string.disabledSummaryPref);
				}
				break;
			default:
				whiteDelayPref.setEnabled(false);
				blackDelayPref.setEnabled(false);
				whiteDelayPref.setSummary(R.string.disabledDelaySummaryPref);
				blackDelayPref.setSummary(R.string.disabledDelaySummaryPref);
				break;
		}
	}	
}
