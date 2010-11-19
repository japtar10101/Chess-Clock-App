/**
 * Helper classes
 */
package com.app.chessclock.models;

import com.app.chessclock.gui.TimerPreference;

import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;

/**
 * Options shared between each layout
 * @author japtar10101
 */
public class OptionsModel implements SaveStateModel {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	// == Stored key values ==
	/** The saved key value for time limit */
	public static final String KEY_TIME_LIMIT = "timeLimit";
	/** The saved key value for time limit (minutes) */
	public static final String KEY_TIME_LIMIT_MINUTES =
		KEY_TIME_LIMIT + TimerPreference.APPEND_KEY_MINUTES;
	/** The saved key value for time limit (seconds) */
	public static final String KEY_TIME_LIMIT_SECONDS =
		KEY_TIME_LIMIT + TimerPreference.APPEND_KEY_SECONDS;
	
	/** The saved key value for delay time */
	public static final String KEY_DELAY_TIME = "delayTime";
	/** The saved key value for delay time (minutes) */
	public static final String KEY_DELAY_TIME_MINUTES =
		KEY_DELAY_TIME + TimerPreference.APPEND_KEY_MINUTES;
	/** The saved key value for delay time (seconds) */
	public static final String KEY_DELAY_TIME_SECONDS =
		KEY_DELAY_TIME + TimerPreference.APPEND_KEY_SECONDS;

	/** The saved key value for alarm */
	public static final String KEY_ALARM = "alarm";
	/** The saved key value for enabling click sound */
	public static final String KEY_CLICK = "click";
	/** The saved key value for vibrating */
	public static final String KEY_VIBRATE = "vibrate";
	
	// == Default values ==
	/** The default time limit (minutes) */
	public static final byte DEFAULT_TIME_LIMIT_MINUTES = 5;
	/** The default time limit (seconds) */
	public static final byte DEFAULT_TIME_LIMIT_SECONDS = 0;
	
	/** The default delay time (minutes) */
	public static final byte DEFAULT_DELAY_TIME_MINUTES = 0;
	/** The default delay time (seconds) */
	public static final byte DEFAULT_DELAY_TIME_SECONDS = 2;

	/** The default value for {@link #enableClick} */
	public static final boolean DEFAULT_ENABLE_CLICK = true;
	/** The default value for {@link #enableVibrate} */
	public static final boolean DEFAULT_ENABLE_VIBRATE = true;
	
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
	public Uri alarmUri = Settings.System.DEFAULT_ALARM_ALERT_URI;
	/** If true, make a sound when pressing a game button */
	public boolean enableClick = DEFAULT_ENABLE_CLICK;
	/** If true, vibrates when clicking any button */
	public boolean enableVibrate = DEFAULT_ENABLE_VIBRATE;

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
		savedTimeLimit.setMinutes(TimeModel.intToByte(savedValue));
		
		savedValue = savedState.getInt(
				KEY_TIME_LIMIT_SECONDS, DEFAULT_TIME_LIMIT_SECONDS);
		savedTimeLimit.setSeconds(TimeModel.intToByte(savedValue));
		
		savedValue = savedState.getInt(
				KEY_DELAY_TIME_MINUTES, DEFAULT_DELAY_TIME_MINUTES);
		savedDelayTime.setMinutes(TimeModel.intToByte(savedValue));
		
		savedValue = savedState.getInt(
				KEY_DELAY_TIME_SECONDS, DEFAULT_DELAY_TIME_SECONDS);
		savedDelayTime.setSeconds(TimeModel.intToByte(savedValue));
		
		String savedUri = savedState.getString(KEY_ALARM, null);
		if(savedUri != null) {
			alarmUri = Uri.parse(savedUri);
		} else {
			alarmUri = Settings.System.DEFAULT_ALARM_ALERT_URI;
		}
		
		enableClick = savedState.getBoolean(KEY_CLICK, DEFAULT_ENABLE_CLICK);
		enableVibrate = savedState.getBoolean(KEY_VIBRATE, DEFAULT_ENABLE_VIBRATE);
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
		
		saveEditor.putString(KEY_ALARM, alarmUri.toString());
		
		saveEditor.putBoolean(KEY_CLICK, enableClick);
		saveEditor.putBoolean(KEY_VIBRATE, enableVibrate);
		
		// Write it in!
		saveEditor.commit();
	}
}
