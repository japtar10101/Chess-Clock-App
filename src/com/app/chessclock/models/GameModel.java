/**
 * 
 */
package com.app.chessclock.models;

import java.security.InvalidParameterException;

import android.os.Bundle;

import com.app.chessclock.enums.TimerCondition;

/**
 * FIXME: finish this model
 * @author japtar10101
 */
public class GameModel {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	/** The saved key value for time limit (minutes) */
	public static final String KEY_TIME_LIMIT_MINUTES = "timeLimitMinutes";
	/** The saved key value for time limit (seconds) */
	public static final String KEY_TIME_LIMIT_SECONDS = "timeLimitSeconds";
	/** The saved key value for delay time (minutes) */
	public static final String KEY_DELAY_TIME_MINUTES = "delayTimeMinutes";
	/** The saved key value for delay time (seconds) */
	public static final String KEY_DELAY_TIME_SECONDS = "delayTimeSeconds";
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The saved state */
	private Bundle mSavedState = null;
	
	// == Conditionals ==
	/** Flag indicating whose turn it is */
	private boolean msLeftPlayersTurn = false;
	/** The current timer's condition */
	public TimerCondition timerCondition = TimerCondition.STARTING;
	
	// == Times ==
	/** Left player's time */
	private final TimeModel msLeftPlayersTime = new TimeModel();
	/** Right player's time */
	private final TimeModel msRightPlayersTime = new TimeModel();
	/** Time of delay */
	private final TimeModel msDelayTime = new TimeModel();
	
	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Saves the current options state to a bundle
	 * @param savedState the bundle to save this app's options
	 */
	public void saveSettings() {
		// Make sure the bundle isn't null
		if(mSavedState != null) {
			// Save the attributes to the bundle
//			mSavedState.putInt(KEY_TIME_LIMIT_MINUTES, savedTimeLimit.getMinutes());
//			mSavedState.putInt(KEY_TIME_LIMIT_SECONDS, savedTimeLimit.getSeconds());
//			mSavedState.putInt(KEY_DELAY_TIME_MINUTES, savedDelayTime.getMinutes());
//			mSavedState.putInt(KEY_DELAY_TIME_SECONDS, savedDelayTime.getSeconds());
			
			// FIXME: save this bundle to some personal SQL database
		}
	}
	
	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mSavedState}
	 */
	public Bundle getSavedState() {
		return mSavedState;
	}
	
	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param savedState sets {@link mSavedState}
	 */
	public void setSavedState(final Bundle savedState) throws InvalidParameterException {
		// Set mSavedState
		mSavedState = savedState;
		
		// Make sure the parameter is correct
		if(mSavedState != null) {
			// Save the attributes to the bundle
//			savedTimeLimit.setMinutes(mSavedState.getInt(
//					KEY_TIME_LIMIT_MINUTES,
//					DEFAULT_TIME_LIMIT_MINUTES));
//			savedTimeLimit.setSeconds(mSavedState.getInt(
//					KEY_TIME_LIMIT_SECONDS,
//					DEFAULT_TIME_LIMIT_SECONDS));
//			savedDelayTime.setMinutes(mSavedState.getInt(
//					KEY_DELAY_TIME_MINUTES,
//					DEFAULT_DELAY_TIME_MINUTES));
//			savedDelayTime.setSeconds(mSavedState.getInt(
//					KEY_DELAY_TIME_MINUTES,
//					DEFAULT_DELAY_TIME_SECONDS));
		}
	}
}
