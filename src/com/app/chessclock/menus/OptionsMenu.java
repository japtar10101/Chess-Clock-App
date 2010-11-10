/**
 * Package of menus
 */
package com.app.chessclock.menus;

import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

import com.app.chessclock.Global;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;
import com.app.chessclock.enums.MenuId;
import com.app.chessclock.enums.TimerCondition;
import com.app.chessclock.models.TimeModel;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class OptionsMenu extends PreferenceActivity implements ActivityMenu, OnClickListener {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The main activities class */
	protected MainActivity mParentActivity;

	// == Buttons ==
	/** "Start Game" button */
	private Button mStartGame = null;
	/** "Cancel" button */
	private Button mCancel = null;
	
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
		
		// Grab the buttons
		mStartGame = (Button)mParentActivity.findViewById(R.id.buttonOk);
		mCancel = (Button)mParentActivity.findViewById(R.id.buttonCancel);
		
		// Set the buttons click behavior to this class
		mStartGame.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		
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

	/**
	 * @return false, always
	 * @see com.app.chessclock.menus.ActivityMenu#enableMenuButton()
	 */
	@Override
	public boolean enableMenuButton() {
		return false;
	}

	/**
	 * TODO: add a description
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(final View view) {
		
		// Check if the Start Game button was selected
		if(view == mStartGame) {
			// If so, update the time limit settings
			TimeModel updateTime = Global.OPTIONS.savedTimeLimit;
			updateTime.setMinutes(TimeModel.intToByte(
					mTimeLimit.getCurrentHour()));
			updateTime.setSeconds(TimeModel.intToByte(
					mTimeLimit.getCurrentMinute()));
			
			// update the delay time settings
			updateTime = Global.OPTIONS.savedDelayTime;
			updateTime.setMinutes(TimeModel.intToByte(
					mDelayTime.getCurrentHour()));
			updateTime.setSeconds(TimeModel.intToByte(
					mDelayTime.getCurrentMinute()));
			
			// FIXME: update the sound settings
			
			// Save the settings, and restart the game
			Global.OPTIONS.saveSettings();
			Global.GAME_STATE.timerCondition = TimerCondition.STARTING;
		}
		
		// Switch the layout to TimersMenu
		mParentActivity.setCurrentMenuId(MenuId.TIMER);
	}

	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */

}
