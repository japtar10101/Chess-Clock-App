/**
 * Helper classes
 */
package com.app.chessclock.models;

import java.security.InvalidParameterException;

/**
 * Helper class representing time.
 * @author japtar10101
 */
public class Time {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** Minutes */
	private Integer mMinutes;
	/** Seconds */
	private Integer mSeconds;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see #setTime(int, int)
	 */
	public Time(final int minutes, final int seconds) {
		this.setTime(minutes, seconds);
	}
	
	/**
	 * Default constructor.  Sets the time to 0.
	 */
	public Time() {
		this(0, 0);
	}

	/* ===========================================================
	 * Override
	 * =========================================================== */
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mMinutes.toString() + ":" + mSeconds.toString();
	}
	
	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Sets the time.
	 * @param minutes sets {@link mMinutes}
	 * @param seconds sets {@link mSeconds}
	 * @throws InvalidParameterException if <b>minutes</b> is negative
	 * @throws InvalidParameterException if <b>seconds</b> is negative,
	 * or greater than 59
	 * @see #setMinutes(int)
	 * @see #setSeconds(int)
	 */
	public void setTime(final int minutes, final int seconds) {
		this.setMinutes(minutes);
		this.setSeconds(seconds);
	}
	
	/**
	 * Sets the time.
	 * @param time time to match
	 */
	public void setTime(final Time time) {
		if(time != null) {
			this.setMinutes(time.getMinutes());
			this.setSeconds(time.getSeconds());
		}
	}
	
	/**
	 * @return True if both the minutes and seconds are 0
	 * @see #getMinutes()
	 * @see #getSeconds() 
	 */
	public boolean isTimeZero() {
		return this.getMinutes() + this.getSeconds() == 0;
	}
	
	/**
	 * Decreases the time by a seconds
	 * @return
	 */
	public boolean decrementASecond() {
		// Check if we're already at 0
		if(isTimeZero()) {
			// return true immediately
			return true;
		} else {
			// check if the seconds is already at 0
			if(mSeconds <= 0) {
				// If so, decrement the minutes
				--mMinutes;
				
				// reset seconds to 59
				mSeconds = 59;
			} else {
				// If not, decrement the seconds 
				--mSeconds;
			}
			return false;
		}
	}

	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mMinutes}
	 */
	public int getMinutes() {
		return mMinutes;
	}
	/**
	 * @return {@link mSeconds}
	 */
	public int getSeconds() {
		return mSeconds;
	}

	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param minutes sets {@link mMinutes}
	 * @throws InvalidParameterException if <b>minutes</b> is negative
	 */
	public void setMinutes(final int minutes) {
		if(minutes < 0) {
			// negative valuemLeftButton
			throw new InvalidParameterException("Minutes must be positive");
		} else {
			// update the minutes
			this.mMinutes = minutes;
		}
	}
	/**
	 * @param seconds sets {@link mSeconds}
	 * @throws InvalidParameterException if <b>seconds</b> is negative,
	 * or greater than 59
	 */
	public void setSeconds(final int seconds) {
		if(seconds < 0) {
			// negative value
			throw new InvalidParameterException("Seconds must be positive");
		} else if(seconds >= 60) {
			// greater than 59
			throw new InvalidParameterException("Seconds must be less than 60");
		} else {
			// update the seconds
			this.mSeconds = seconds;
		}
	}
}
