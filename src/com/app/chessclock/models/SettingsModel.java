/**
 * Helper classes
 */
package com.app.chessclock.models;

import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Options shared between each layout
 * @author japtar10101
 */
public class SettingsModel implements SaveStateModel {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	// == Stored key values ==
	/** The saved key value for time limit (minutes) */
	public static final String KEY_TIME_LIMIT_MINUTES = "timeLimitMinutes";
	/** The saved key value for time limit (seconds) */
	public static final String KEY_TIME_LIMIT_SECONDS = "timeLimitSeconds";
	
	/** The saved key value for delay time (minutes) */
	public static final String KEY_DELAY_TIME_MINUTES = "delayTimeMinutes";
	/** The saved key value for delay time (seconds) */
	public static final String KEY_DELAY_TIME_SECONDS = "delayTimeSeconds";

	/** The saved key value for delay time (seconds) */
	public static final String KEY_ALARM = "alarm";
	/** The saved key value for delay time (seconds) */
	public static final String KEY_CLICK_MODE = "clickMode";
	
	// == Default values ==
	/** The default time limit (minutes) */
	public static final byte DEFAULT_TIME_LIMIT_MINUTES = 5;
	/** The default time limit (seconds) */
	public static final byte DEFAULT_TIME_LIMIT_SECONDS = 0;
	
	/** The default delay time (minutes) */
	public static final byte DEFAULT_DELAY_TIME_MINUTES = 0;
	/** The default delay time (seconds) */
	public static final byte DEFAULT_DELAY_TIME_SECONDS = 2;

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
	/** The stored alarm */
	public Uri alarmUri = RingtoneManager.getDefaultUri(
			RingtoneManager.TYPE_ALARM);
	// FIXME: add click or vibrate

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	/**
	 * Saves the current options state to a bundle
	 * @see com.app.chessclock.models.SaveStateModel#recallSettings(android.content.SharedPreferences)
	 */
	@Override
	public void recallSettings(SharedPreferences savedState) throws
			IllegalArgumentException {
		// Make sure the parameter isn't null
		if(savedState == null) {
			// If so, complain
			throw new IllegalArgumentException("savedState cannot be null");
		}
		
		// Recall the attributes to the bundle
		int savedValue = savedState.getInt(
				KEY_TIME_LIMIT_MINUTES, DEFAULT_TIME_LIMIT_MINUTES);
		byte valueToSet = (savedValue <= Byte.MAX_VALUE ?
				(byte) savedValue : Byte.MAX_VALUE);
		savedTimeLimit.setMinutes(valueToSet);
		
		savedValue = savedState.getInt(
				KEY_TIME_LIMIT_SECONDS, DEFAULT_TIME_LIMIT_SECONDS);
		valueToSet = (savedValue <= Byte.MAX_VALUE ?
				(byte) savedValue : Byte.MAX_VALUE);
		savedTimeLimit.setSeconds(valueToSet);
		
		savedValue = savedState.getInt(
				KEY_DELAY_TIME_MINUTES, DEFAULT_DELAY_TIME_MINUTES);
		valueToSet = (savedValue <= Byte.MAX_VALUE ?
				(byte) savedValue : Byte.MAX_VALUE);
		savedDelayTime.setMinutes(valueToSet);
		
		savedValue = savedState.getInt(
				KEY_DELAY_TIME_SECONDS, DEFAULT_DELAY_TIME_SECONDS);
		valueToSet = (savedValue <= Byte.MAX_VALUE ?
				(byte) savedValue : Byte.MAX_VALUE);
		savedDelayTime.setSeconds(valueToSet);
		
		String savedUri = savedState.getString(KEY_ALARM, null);
		if(savedUri != null) {
			alarmUri = Uri.parse(savedUri);
		} else {
			alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		}
	}

	/**
	 * Saves options
	 * @see com.app.chessclock.models.SaveStateModel#saveSettings(android.content.SharedPreferences)
	 */
	@Override
	public void saveSettings(final SharedPreferences saveState) throws
			IllegalArgumentException {
		// Make sure the parameter isn't null
		if(saveState == null) {
			// If so, complain
			throw new IllegalArgumentException("saveState cannot be null");
		}
		
		// Grab the editor, and start saving
		final SharedPreferences.Editor saveEditor = saveState.edit();
		
		// Save the attributes to the bundle
		saveEditor.putInt(KEY_TIME_LIMIT_MINUTES, savedTimeLimit.getMinutes());
		saveEditor.putInt(KEY_TIME_LIMIT_SECONDS, savedTimeLimit.getSeconds());
		
		saveEditor.putInt(KEY_DELAY_TIME_MINUTES, savedDelayTime.getMinutes());
		saveEditor.putInt(KEY_DELAY_TIME_SECONDS, savedDelayTime.getSeconds());
		
		// Write it in!
		saveEditor.commit();
	}
}
