/**
 * Helper classes
 */
package com.japtar.chessclock.models;

import java.security.InvalidParameterException;

import android.content.SharedPreferences;

/**
 * Helper class representing time.
 * @author japtar10101
 */
public class TimeModel {
	/* ===========================================================
	 * Static functions
	 * =========================================================== */
	/**
	 * @return Converts <b>integer</b> into a byte
	 */
	public final static byte intToByte(final int integer) {
		return (integer <= Byte.MAX_VALUE ? (byte) integer : Byte.MAX_VALUE);
	}
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** Minutes */
	private byte mMinutes;
	/** Seconds */
	private byte mSeconds;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * Constructor
	 */
	public TimeModel(final byte minutes, final byte seconds) throws
			InvalidParameterException {
		this.setSeconds(seconds);
		mMinutes = minutes;
	}
	
	/**
	 * Constructor for saved values.
	 */
	public TimeModel(final int storedValue) throws InvalidParameterException {
		// Make sure this value is valid
		if(storedValue < 0) {
			throw new InternalError(
					"The stored value must be a non-negative value");
		}
		
		// Convert the stored value into seconds
		this.setSeconds(TimeModel.intToByte(storedValue % 60));
		
		// Convert the stored value into seconds
		mMinutes = TimeModel.intToByte(storedValue / 60);
	}
	
	/**
	 * Default constructor.  Sets the time to 0.
	 */
	public TimeModel() {
		mSeconds = 0;
		mMinutes = 0;
	}

	/* ===========================================================
	 * Override
	 * =========================================================== */
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Generate a string, starting with the minutes
		String toReturn = Byte.toString(mMinutes) + ':';
		
		// Append the seconds
		if(mSeconds < 10) {
			// If seconds is less than 10, append a 0
			toReturn += '0';
		}
		toReturn += Byte.toString(mSeconds);
		
		return toReturn;
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
	 * @see #setMinutes(byte)
	 * @see #setSeconds(byte)
	 */
	public void setTime(final byte minutes, final byte seconds) throws
			InvalidParameterException {
		this.setSeconds(seconds);
		mMinutes = minutes;
	}
	
	/**
	 * Sets the time.
	 * @param time time to match
	 */
	public void setTime(final TimeModel time) {
		if(time != null) {
			this.setSeconds(time.getSeconds());
			mMinutes = time.getMinutes();
		}
	}
	
	/**
	 * Sets the time based on the saved state's value
	 * @param savedState
	 * @param key
	 * @param defaultValue
	 * @throws InvalidParameterException 
	 * @throws InternalError 
	 */
	public void recallTime(final SharedPreferences savedState,
			final String key, final int defaultValue) throws
			InvalidParameterException, InternalError {
		// Make sure the parameters are valid
		if(savedState == null) {
			throw new InvalidParameterException("SharedPreference is null");
		} else if(key == null) {
			throw new InvalidParameterException("String is null");
		}
		
		// Recall the attributes from the bundle
		final int savedValue = savedState.getInt(key, defaultValue);
		
		// Make sure this value is valid
		if(savedValue < 0) {
			throw new InternalError(
					"The stored value must be a non-negative value");
		}
		
		// Convert the stored value into seconds
		this.setSeconds(TimeModel.intToByte(savedValue % 60));
		
		// Convert the stored value into seconds
		mMinutes = TimeModel.intToByte(savedValue / 60);
	}
	
	/**
	 * Stores the time
	 * @param saveEditor
	 * @param key
	 * @throws InvalidParameterException
	 */
	public void saveTime(final SharedPreferences.Editor saveEditor,
			final String key) throws InvalidParameterException {
		// Make sure the parameters are valid
		if(saveEditor == null) {
			throw new InvalidParameterException("SharedPreference is null");
		} else if(key == null) {
			throw new InvalidParameterException("String is null");
		}
		
		// Calculate the accumulative number of seconds 
		final int valueToSave = (mMinutes * 60) + mSeconds;
	
		// Save the attributes to the bundle
		saveEditor.putInt(key, valueToSave);
	}
	
	/**
	 * @return True if both the minutes and seconds are 0
	 * @see #getMinutes()
	 * @see #getSeconds() 
	 */
	public boolean isTimeZero() {
		return mMinutes + mSeconds == 0;
	}
	
	/**
	 * Decreases the time by a seconds
	 * @return False if both minutes and seconds are 0
	 * @see #isTimeZero()
	 */
	public boolean decrementASecond() {
		boolean toReturn = !isTimeZero();
		
		// Check if we're not at 0
		if(toReturn) {
			// check if the seconds is 0 or not
			if(mSeconds > 0) {
				// If not, decrement the seconds 
				--mSeconds;
			} else {
				// If so, decrement the minutes
				--mMinutes;
				
				// reset seconds to 59
				mSeconds = 59;
			}
		}
		
		return toReturn;
	}

	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mMinutes}
	 */
	public byte getMinutes() {
		return mMinutes;
	}
	/**
	 * @return {@link mSeconds}
	 */
	public byte getSeconds() {
		return mSeconds;
	}

	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param minutes sets {@link mMinutes}
	 * @throws InvalidParameterException if <b>minutes</b> is negative,
	 * or greater than 23 (as arbitrary as it sounds, due to current
	 * GUI limitations, it's set to the hours limitations on a TimePicker).
	 */
	public void setMinutes(final byte minutes) {
		// update the minutes
		this.mMinutes = minutes;
	}
	
	/**
	 * @param seconds sets {@link mSeconds}
	 * @throws InvalidParameterException if <b>seconds</b> is negative,
	 * or greater than 59
	 */
	public void setSeconds(final byte seconds) throws InvalidParameterException {
		if(seconds >= 60) {
			// greater than 59
			throw new InvalidParameterException("Seconds must be less than 60");
		} else {
			// update the seconds
			this.mSeconds = seconds;
		}
	}
}
