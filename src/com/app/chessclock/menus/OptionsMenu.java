/**
 * Package of menus
 */
package com.app.chessclock.menus;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.app.chessclock.Global;
import com.app.chessclock.R;
import com.app.chessclock.gui.TimerPreference;
import com.app.chessclock.models.TimeModel;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity implements ActivityMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	
	// == TimePicker ==
	/** "Time Limit" picker */
	private TimerPreference mTimeLimit = null;
	/** "Delay Time" picker */
	private TimerPreference mDelayTime = null;
	
	// == Selection box button ==
	// FIXME: add sound-related GUI
	
	// == Selection dialog ==
	// FIXME: add sound-related GUI

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
	    this.setupMenu();
	}
	
	/**
	 * Sets up the Menu
	 * @see com.app.chessclock.menus.ActivityMenu#setupLayout(android.app.Activity)
	 */
	@Override
	public void setupMenu() {
		// First, setup the UI
		this.addPreferencesFromResource(R.layout.options);
		
		// Grab the TimePickers
		String guiKey = this.getString(R.string.prefTimeLimit);
		mTimeLimit = (TimerPreference) this.findPreference(guiKey);
		guiKey = this.getString(R.string.prefTimeDelay);
		mDelayTime = (TimerPreference) this.findPreference(guiKey);
		
		// Update the TimePickers to current settings
		mTimeLimit.setModel(Global.OPTIONS.savedTimeLimit);
		mDelayTime.setModel(Global.OPTIONS.savedDelayTime);
	}

	/**
	 * Saves the settings
	 * @see com.app.chessclock.menus.ActivityMenu#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitMenu() {}

	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */

}
