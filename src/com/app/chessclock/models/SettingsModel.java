/**
 * Helper classes
 */
package com.app.chessclock.models;

import java.security.InvalidParameterException;

import android.os.Bundle;

/**
 * Options shared between each layout
 * @author japtar10101
 */
public class SettingsModel {
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

	/** The default time limit (minutes) */
	public static final int DEFAULT_TIME_LIMIT_MINUTES = 5;
	/** The default time limit (seconds) */
	public static final int DEFAULT_TIME_LIMIT_SECONDS = 0;
	
	/** The default delay time (minutes) */
	public static final int DEFAULT_DELAY_TIME_MINUTES = 0;
	/** The default delay time (seconds) */
	public static final int DEFAULT_DELAY_TIME_SECONDS = 2;

	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == Settings saved ==
	/** The time limit */
	public final TimeModel savedTimeLimit =
		new TimeModel(DEFAULT_TIME_LIMIT_MINUTES, DEFAULT_TIME_LIMIT_SECONDS);
	/** The delay time */
	public final TimeModel savedDelayTime =
		new TimeModel(DEFAULT_DELAY_TIME_MINUTES, DEFAULT_DELAY_TIME_SECONDS);
	// FIXME: add a sound
	/** The saved state */
	private Bundle mSavedState = null;

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
			mSavedState.putInt(KEY_TIME_LIMIT_MINUTES, savedTimeLimit.getMinutes());
			mSavedState.putInt(KEY_TIME_LIMIT_SECONDS, savedTimeLimit.getSeconds());
			
			mSavedState.putInt(KEY_DELAY_TIME_MINUTES, savedDelayTime.getMinutes());
			mSavedState.putInt(KEY_DELAY_TIME_SECONDS, savedDelayTime.getSeconds());
			
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
			savedTimeLimit.setMinutes(mSavedState.getInt(
					KEY_TIME_LIMIT_MINUTES,
					DEFAULT_TIME_LIMIT_MINUTES));
			savedTimeLimit.setSeconds(mSavedState.getInt(
					KEY_TIME_LIMIT_SECONDS,
					DEFAULT_TIME_LIMIT_SECONDS));
			
			savedDelayTime.setMinutes(mSavedState.getInt(
					KEY_DELAY_TIME_MINUTES,
					DEFAULT_DELAY_TIME_MINUTES));
			savedDelayTime.setSeconds(mSavedState.getInt(
					KEY_DELAY_TIME_SECONDS,
					DEFAULT_DELAY_TIME_SECONDS));
		}
	}
}
