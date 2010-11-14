/**
 * Package of menus
 */
package com.app.chessclock.menus;

import android.preference.PreferenceActivity;
import android.widget.TimePicker;

import com.app.chessclock.Global;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;
import com.app.chessclock.models.TimeModel;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity implements ActivityMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The main activities class */
	protected MainActivity mParentActivity;
	
	// == TimePicker ==
	/** "Time Limit" picker */
	private TimePicker mTimeLimit = null;
	/** "Delay Time" picker */
	private TimePicker mDelayTime = null;
	
	// == Selection box button ==
	// FIXME: add sound-related GUI
	
	// == Selection dialog ==
	// FIXME: add sound-related GUI
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @param parent the menu's parent activity
	 */
	public OptionsMenu(final MainActivity parent) {
		// Setup activity
		mParentActivity = parent;
		
		// Setup the settings
		this.addPreferencesFromResource(R.layout.settings);
	}

	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * Sets up the Menu
	 * @see com.app.chessclock.menus.ActivityMenu#setupLayout(android.app.Activity)
	 */
	@Override
	public void setupMenu() {
		// FIXME: launch an intent here.
		// First, setup the UI
		mParentActivity.setContentView(R.layout.options);
		
		// Grab the TimePickers
		mTimeLimit = (TimePicker)mParentActivity.findViewById(R.id.timeTimeLimit);
		mDelayTime = (TimePicker)mParentActivity.findViewById(R.id.timeDelayTime);
		
		// Hack: remove the AM/PM options
		mTimeLimit.setIs24HourView(true);
		mDelayTime.setIs24HourView(true);
		
		// Update the TimePickers to current settings
		TimeModel updateTime = Global.OPTIONS.savedTimeLimit;
		mTimeLimit.setCurrentHour(Integer.valueOf(updateTime.getMinutes()));
		mTimeLimit.setCurrentMinute(Integer.valueOf(updateTime.getSeconds()));
		
		updateTime = Global.OPTIONS.savedDelayTime;
		mDelayTime.setCurrentHour(Integer.valueOf(updateTime.getMinutes()));
		mDelayTime.setCurrentMinute(Integer.valueOf(updateTime.getSeconds()));
	}

	/**
	 * Saves the settings
	 * @see com.app.chessclock.menus.ActivityMenu#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitMenu() {
		// If we're on options, first save the game options
		Global.OPTIONS.saveSettings();
	}

	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */

}
