/**
 * 
 */
package com.app.chessclock.options;

import java.security.InvalidParameterException;

import android.os.Bundle;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class TimerOption {
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
	/** The time limit (minutes) */
	private int mTimeLimitMinutes;
	/** The time limit (seconds) */
	private int mTimeLimitSeconds;
	/** The delay time (minutes) */
	private int mDelayTimeMinutes;
	/** The delay time (seconds) */
	private int mDelayTimeSeconds;

	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * Default constructor.  Sets the time to default parameters.
	 */
	public TimerOption() {
		this.setTimeLimitMinutes(DEFAULT_TIME_LIMIT_MINUTES);
		this.setTimeLimitSeconds(DEFAULT_TIME_LIMIT_SECONDS);
		this.setDelayTimeMinutes(DEFAULT_DELAY_TIME_MINUTES);
		this.setDelayTimeSeconds(DEFAULT_DELAY_TIME_SECONDS);
	}
	
	/**
	 * Constructor for saved state.  Recalls the options for this application.
	 * @param savedState this application's saved state
	 */
	public TimerOption(final Bundle savedState) {
		// Set the values to default parameters
		this();
		
		// Try to recall any saved options
		if(savedState != null) {
			this.loadOptions(savedState);
		}
	}

	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Saves the current options state to a bundle
	 * @param savedState the bundle to save this app's options
	 * @throws InvalidParameterException if <b>savedState</b> is null
	 */
	public void saveOptions(final Bundle savedState) {
		// Make sure the parameter is correct
		if(savedState == null) {
			throw new InvalidParameterException("Saved State cannot be null");
		}
		
		// Save the attributes to the bundle
		savedState.putInt(KEY_TIME_LIMIT_MINUTES, mTimeLimitMinutes);
		savedState.putInt(KEY_TIME_LIMIT_SECONDS, mTimeLimitSeconds);
		savedState.putInt(KEY_DELAY_TIME_MINUTES, mDelayTimeMinutes);
		savedState.putInt(KEY_DELAY_TIME_SECONDS, mDelayTimeSeconds);
	}
	
	/**
	 * Loads the bundle's state to options
	 * @param savedState the bundle to load this app's options
	 * @throws InvalidParameterException if <b>savedState</b> is null
	 */
	public void loadOptions(final Bundle savedState) {
		// Make sure the parameter is correct
		if(savedState == null) {
			throw new InvalidParameterException("Saved State cannot be null");
		}
		
		// Save the attributes to the bundle
		this.setTimeLimitMinutes(savedState.getInt(
				KEY_TIME_LIMIT_MINUTES,
				DEFAULT_TIME_LIMIT_MINUTES));
		this.setTimeLimitSeconds(savedState.getInt(
				KEY_TIME_LIMIT_SECONDS,
				DEFAULT_TIME_LIMIT_SECONDS));
		this.setDelayTimeMinutes(savedState.getInt(
				KEY_DELAY_TIME_MINUTES,
				DEFAULT_DELAY_TIME_MINUTES));
		this.setDelayTimeSeconds(savedState.getInt(
				KEY_DELAY_TIME_MINUTES,
				DEFAULT_DELAY_TIME_SECONDS));
	}

	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mTimeLimitMinutes}
	 */
	public int getTimeLimitMinutes() {
		return mTimeLimitMinutes;
	}

	/**
	 * @return {@link mTimeLimitSeconds}
	 */
	public int getTimeLimitSeconds() {
		return mTimeLimitSeconds;
	}

	/**
	 * @return {@link mDelayTimeMinutes}
	 */
	public int getDelayTimeMinutes() {
		return mDelayTimeMinutes;
	}

	/**
	 * @return {@link mDelayTimeSeconds}
	 */
	public int getDelayTimeSeconds() {
		return mDelayTimeSeconds;
	}
	
	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param timeLimitMinutes sets {@link mTimeLimitMinutes}
	 * @throws InvalidParameterException if <b>timeLimitMinutes</b> is negative
	 */
	public void setTimeLimitMinutes(int timeLimitMinutes) {
		// Make sure the parameter is correct
		this.verifyMinutes(timeLimitMinutes);
		
		// Set the variable
		this.mTimeLimitMinutes = timeLimitMinutes;	
	}

	/**
	 * @param timeLimitSeconds sets {@link mTimeLimitSeconds}
	 * @throws InvalidParameterException if <b>timeLimitSeconds</b> is negative,
	 * or greater than 59
	 */
	public void setTimeLimitSeconds(int timeLimitSeconds) {
		// Make sure the parameter is correct
		this.verifySeconds(timeLimitSeconds);
		
		// Set the variable
		this.mTimeLimitSeconds = timeLimitSeconds;
	}

	/**
	 * @param delayTimeMinutes sets {@link mDelayTimeMinutes}
	 * @throws InvalidParameterException if <b>delayTimeMinutes</b> is negative
	 */
	public void setDelayTimeMinutes(int delayTimeMinutes) {
		// Make sure the parameter is correct
		this.verifyMinutes(delayTimeMinutes);
		
		// Set the variable
		this.mDelayTimeMinutes = delayTimeMinutes;
	}

	/**
	 * @param delayTimeSeconds sets {@link mDelayTimeSeconds}
	 * @throws InvalidParameterException if <b>delayTimeSeconds</b> is negative,
	 * or greater than 59
	 */
	public void setDelayTimeSeconds(int delayTimeSeconds) {
		// Make sure the parameter is correct
		this.verifySeconds(delayTimeSeconds);
		
		// Set the variable
		this.mDelayTimeSeconds = delayTimeSeconds;	
	}
	
	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	/**
	 * Helper function solely used to throw exceptions.<br/>
	 * Verifies the minutes are valid.
	 * @param minutes value to check
	 * @throws InvalidParameterException if <b>minutes</b> is negative
	 */
	private void verifyMinutes(int minutes) {
		if(minutes < 0) {
			// negative value
			throw new InvalidParameterException("Minutes must be positive");
		}
	}
	
	/**
	 * Helper function solely used to throw exceptions.<br/>
	 * Verifies the seconds are valid.
	 * @param seconds value to check
	 * @throws InvalidParameterException if <b>seconds</b> is negative,
	 * or greater than 59
	 */
	private void verifySeconds(int seconds) {
		if(seconds < 0) {
			// negative value
			throw new InvalidParameterException("Seconds must be positive");
		} else if(seconds >= 60) {
			// greater than 59
			throw new InvalidParameterException("Seconds must be less than 60");
		}
	}
}
