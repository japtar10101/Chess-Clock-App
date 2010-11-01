/**
 * 
 */
package com.app.chessclock.models;

import java.security.InvalidParameterException;

import android.os.Bundle;

/**
 * Options shared between each layout
 * @author japtar10101
 */
public class Options {
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
	/** The time limit */
	private final Time mTimeLimit =
		new Time(DEFAULT_TIME_LIMIT_MINUTES, DEFAULT_TIME_LIMIT_SECONDS);
	/** The delay time */
	private final Time mDelayTime =
		new Time(DEFAULT_DELAY_TIME_MINUTES, DEFAULT_DELAY_TIME_SECONDS);
	/** The saved state */
	private Bundle mSavedState = null;
	
	/** Variable used between layouts.
	 * Determines whether the Timer is paused or not */
	public boolean isPaused = false;

	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Saves the current options state to a bundle
	 * @param savedState the bundle to save this app's options
	 */
	public void saveOptions() {
		// Make sure the bundle isn't null
		if(mSavedState != null) {
			// Save the attributes to the bundle
			mSavedState.putInt(KEY_TIME_LIMIT_MINUTES, mTimeLimit.getMinutes());
			mSavedState.putInt(KEY_TIME_LIMIT_SECONDS, mTimeLimit.getSeconds());
			mSavedState.putInt(KEY_DELAY_TIME_MINUTES, mDelayTime.getMinutes());
			mSavedState.putInt(KEY_DELAY_TIME_SECONDS, mDelayTime.getSeconds());
		}
	}
	
	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mTimeLimit}
	 */
	public Time getTimeLimit() {
		return mTimeLimit;
	}

	/**
	 * @return {@link mDelayTime}
	 */
	public Time getDelayTime() {
		return mDelayTime;
	}
	
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
			mTimeLimit.setMinutes(mSavedState.getInt(
					KEY_TIME_LIMIT_MINUTES,
					DEFAULT_TIME_LIMIT_MINUTES));
			mTimeLimit.setSeconds(mSavedState.getInt(
					KEY_TIME_LIMIT_SECONDS,
					DEFAULT_TIME_LIMIT_SECONDS));
			mDelayTime.setMinutes(mSavedState.getInt(
					KEY_DELAY_TIME_MINUTES,
					DEFAULT_DELAY_TIME_MINUTES));
			mDelayTime.setSeconds(mSavedState.getInt(
					KEY_DELAY_TIME_MINUTES,
					DEFAULT_DELAY_TIME_SECONDS));
		}
	}
}
