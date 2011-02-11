/**
 * Chess Clock App
 * https://github.com/japtar10101/Chess-Clock-App
 * 
 * The MIT License
 * 
 * Copyright (c) 2011 Taro Omiya
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.japtar.chessclock.menus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
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
	public static final String KEY_TIME_GROUP_NO_HANDICAP = "timeNoHandicapGroup";
	/** The key value for all time-related stuff */
	public static final String KEY_TIME_GROUP_WITH_HANDICAP = "timeWithHandicapGroup";
	/** The key value for all time-related stuff */
	public static final String KEY_ADVANCED_GROUP = "advancedOptions";
	
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
		this.updateAllPreferences();
		
		// Set the list preference's listener to this
		this.findPreference(OptionsModel.KEY_DELAY_MODE).
				setOnPreferenceChangeListener(this);
		this.findPreference(OptionsModel.KEY_HANDICAP).
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
        
        // Recall all the settings
        this.recallSettings();
        
        // Check if the handicap is enabled
        if(!Global.OPTIONS.enableHandicap) {
            // If the handicap is not enabled, set the black player's time
            // the same as the white player
        	Global.OPTIONS.savedBlackTimeLimit.setTime(
        			Global.OPTIONS.savedWhiteTimeLimit);
        	Global.OPTIONS.savedBlackDelayTime.setTime(
        			Global.OPTIONS.savedWhiteDelayTime);
        	
        	// Save this settings
        	Global.OPTIONS.saveSettings(
    			PreferenceManager.getDefaultSharedPreferences(this));
        }
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
		this.updateAllPreferences();
    }
    
	/**
	 * Update the delay mode description
	 * @see android.preference.Preference.OnPreferenceChangeListener#onPreferenceChange(android.preference.Preference, java.lang.Object)
	 */
	@Override
	public boolean onPreferenceChange(final Preference preference,
			final Object newValue) {
		// Figure out which preference changed
		final String key = preference.getKey();
		if(key.equals(OptionsModel.KEY_DELAY_MODE)) {
			// == Game mode changed ==
			this.recallSettings();
			// Convert to string
			this.updateAllPreferences(
					(String) newValue,
					Global.OPTIONS.enableHandicap);
		} else if(key.equals(OptionsModel.KEY_HANDICAP)) {
			// == Handicap changed ==
			this.recallSettings();
			// Update the delay preference text	
			this.updateAllPreferences(
					DelayMode.BYTE_TO_STRING[Global.OPTIONS.delayMode],
					(Boolean) newValue);
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
    
    private void updateAllPreferences() {
    	this.updateAllPreferences(
    			DelayMode.BYTE_TO_STRING[Global.OPTIONS.delayMode],
    			Global.OPTIONS.enableHandicap);
    }
    
    /**
     * TODO: document
     */
    private void updateAllPreferences(final String delayMode,
    		final boolean isHandicapEnabled) {
    	/*
    	 * Step 1: Update the text on all delay labels
    	 * Step 2: Disable the necessary preferences.
    	 * Step 3: update the text on each preference why it's disabled.
    	 */
    	
    	// First, update all the delay preference text based on delay mode
    	this.updateDelayPreference(delayMode);
    	
    	// Disable the necessary elements
    	if(Global.GAME_STATE.timerCondition == TimerCondition.RUNNING ||
    			Global.GAME_STATE.timerCondition == TimerCondition.PAUSE) {
    		// Disable all time-related categories
    		this.disablePreferenceGroup(KEY_TIME_GROUP_NO_HANDICAP,
    				R.string.disabledSummaryPref);
    		this.disablePreferenceGroup(KEY_TIME_GROUP_WITH_HANDICAP,
    				R.string.disabledSummaryPref);
    		this.disablePreferenceGroup(KEY_ADVANCED_GROUP,
    				R.string.disabledSummaryPref);
    	} else if(isHandicapEnabled) {
    		// Disable the no-handicap category
    		this.disablePreferenceGroup(KEY_TIME_GROUP_NO_HANDICAP,
    				R.string.disabledNoHandicapSummaryPref);
    	} else {
    		// Disable the with-handicap category
    		this.disablePreferenceGroup(KEY_TIME_GROUP_WITH_HANDICAP,
    				R.string.disabledHandicapSummaryPref);
    	}
    }
    
    /**
     * TODO: document
     */
    private void updateDelayPreference(final String delayMode) {
    	// First, get the delay preferences
    	final Preference[] delayPrefs = new Preference[3];
    	final int[] delayPrefStrings = new int[delayPrefs.length * 2];
    	final Preference[] durationPrefs = new Preference[3];
    	final int[] durationPrefStrings = new int[durationPrefs.length];
    	boolean enablePref = true;
    	
    	// One preference from the "no-handicap" category
    	PreferenceGroup groupPref = (PreferenceGroup)
    			this.findPreference(KEY_TIME_GROUP_NO_HANDICAP);
    	int delayIndex = 0, durationIndex = 0;
    	delayPrefs[delayIndex++] = groupPref.findPreference(
    			OptionsModel.KEY_WHITE_DELAY_TIME);
    	durationPrefs[durationIndex++] = groupPref.findPreference(
    			OptionsModel.KEY_WHITE_TIME_LIMIT);
    	
    	// two preference from the "with-handicap" category
    	groupPref = (PreferenceGroup)
    			this.findPreference(KEY_TIME_GROUP_WITH_HANDICAP);
    	delayPrefs[delayIndex++] = groupPref.findPreference(
    			OptionsModel.KEY_WHITE_DELAY_TIME);
    	delayPrefs[delayIndex++] = groupPref.findPreference(
    			OptionsModel.KEY_BLACK_DELAY_TIME);
    	durationPrefs[durationIndex++] = groupPref.findPreference(
    			OptionsModel.KEY_WHITE_TIME_LIMIT);
    	durationPrefs[durationIndex++] = groupPref.findPreference(
    			OptionsModel.KEY_BLACK_TIME_LIMIT);
    	
    	// Grab the string ID for each index
		delayIndex = 0;
    	if(delayMode.equals(DelayMode.STRING_BASIC)) {
    		delayPrefStrings[delayIndex++] = R.string.basicDelayPref;
    		delayPrefStrings[delayIndex++] = R.string.basicDelaySummaryPref;
    		delayPrefStrings[delayIndex++] = R.string.basicDelayPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.basicDelaySummaryPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.basicDelayPrefBlack;
    		delayPrefStrings[delayIndex++] = R.string.basicDelaySummaryPrefBlack;
		} else if((delayMode.equals(DelayMode.STRING_FISCHER)) ||
				(delayMode.equals(DelayMode.STRING_FISCHER_AFTER)))  {
    		delayPrefStrings[delayIndex++] = R.string.fischerPref;
    		delayPrefStrings[delayIndex++] = R.string.fischerSummaryPref;
    		delayPrefStrings[delayIndex++] = R.string.fischerPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.fischerSummaryPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.fischerPrefBlack;
    		delayPrefStrings[delayIndex++] = R.string.fischerSummaryPrefBlack;
		} else if(delayMode.equals(DelayMode.STRING_BRONSTEIN)) {
    		delayPrefStrings[delayIndex++] = R.string.bronsteinPref;
    		delayPrefStrings[delayIndex++] = R.string.bronsteinSummaryPref;
    		delayPrefStrings[delayIndex++] = R.string.bronsteinPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.bronsteinSummaryPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.bronsteinPrefBlack;
    		delayPrefStrings[delayIndex++] = R.string.bronsteinSummaryPrefBlack;
		} else {
    		enablePref = false;
    		delayPrefStrings[delayIndex++] = R.string.basicDelayPref;
    		delayPrefStrings[delayIndex++] = R.string.disabledDelaySummaryPref;
    		delayPrefStrings[delayIndex++] = R.string.basicDelayPrefWhite;
    		delayPrefStrings[delayIndex++] = R.string.disabledDelaySummaryPref;
    		delayPrefStrings[delayIndex++] = R.string.basicDelayPrefBlack;
    		delayPrefStrings[delayIndex++] = R.string.disabledDelaySummaryPref;
		}
    	
    	// Get the string IDs for duration preferences
    	durationIndex = 0;
    	durationPrefStrings[durationIndex++] = R.string.gameDurationSummaryPref;
    	durationPrefStrings[durationIndex++] = R.string.gameDurationSummaryPrefWhite;
    	durationPrefStrings[durationIndex++] = R.string.gameDurationSummaryPrefBlack;
    	
    	// Update all of these preferences
    	for(delayIndex = 0; delayIndex < delayPrefs.length; ++delayIndex) {
    		durationIndex = delayIndex * 2;
    		delayPrefs[delayIndex].setEnabled(enablePref);
    		delayPrefs[delayIndex].setTitle(
    				delayPrefStrings[durationIndex]);
    		delayPrefs[delayIndex].setSummary(
    				delayPrefStrings[durationIndex + 1]);
    	}
    	for(durationIndex = 0; durationIndex < durationPrefs.length; ++durationIndex) {
    		durationPrefs[durationIndex].setEnabled(true);
    		durationPrefs[durationIndex].setSummary(
    				durationPrefStrings[durationIndex]);
    	}    	
    }
    
    /**
     * TODO: document
     */
    private void disablePreferenceGroup(final String groupKey,
    		final int summaryStringID) {
    	// Grab the preference group, and its size
    	final PreferenceGroup groupPref = (PreferenceGroup)
				this.findPreference(groupKey);
    	final int numPrefs = groupPref.getPreferenceCount();
    	
    	// Iterate through each child preference, and update their text
    	Preference editPref;
    	for(int index = 0; index < numPrefs; ++index) {
    		editPref = groupPref.getPreference(index);
    		editPref.setSummary(summaryStringID);
    	}
    	
    	// Disable the entire group
    	groupPref.setEnabled(false);
    }
}
