/**
 * Helper classes
 */
package com.japtar.chessclock.models;

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
	
	/** The saved key value for delay time */
	public static final String KEY_DELAY_TIME = "delayTime";

	/** The saved key value for alarm */
	public static final String KEY_ALARM = "alarm";
	/** The saved key value for enabling click sound */
	public static final String KEY_CLICK = "click";
	/** The saved key value for vibrating */
	public static final String KEY_VIBRATE = "vibrate";
	
	// == Default values ==
	/** The default time limit */
	public static final int DEFAULT_TIME_LIMIT = 300;
	
	/** The default delay time */
	public static final byte DEFAULT_DELAY_TIME = 2;

	/** The default value for {@link #enableClick} */
	public static final boolean DEFAULT_ENABLE_CLICK = true;
	/** The default value for {@link #enableVibrate} */
	public static final boolean DEFAULT_ENABLE_VIBRATE = true;
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == Settings saved ==
	/** The time limit */
	public final TimeModel savedTimeLimit = new TimeModel(DEFAULT_TIME_LIMIT);
	/** The delay time */
	public final TimeModel savedDelayTime = new TimeModel(DEFAULT_DELAY_TIME);
	/** The stored alarm */
	public Uri alarmUri = null;
	/** If true, make a sound when pressing a game button */
	public boolean enableClick = DEFAULT_ENABLE_CLICK;
	/** If true, vibrates when clicking any button */
	public boolean enableVibrate = DEFAULT_ENABLE_VIBRATE;

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	/**
	 * Saves the current options state to a bundle
	 * @see com.japtar.chessclock.models.SaveStateModel#recallSettings(android.content.SharedPreferences)
	 */
	@Override
	public void recallSettings(final SharedPreferences savedState) throws
			IllegalArgumentException {
		// Make sure the parameter isn't null
		if(savedState == null) {
			// If so, complain
			throw new IllegalArgumentException("savedState cannot be null");
		}
		
		// Recall the attributes to the bundle
		savedTimeLimit.recallTime(savedState, KEY_TIME_LIMIT,
				DEFAULT_TIME_LIMIT);
		savedDelayTime.recallTime(savedState, KEY_DELAY_TIME,
				DEFAULT_DELAY_TIME);
		
		final String savedUri = savedState.getString(KEY_ALARM, null);
		alarmUri = null;
		if((savedUri != null) && (savedUri.length() > 0)) {
			if(savedUri.equals("defaultRingtone")) {
				alarmUri = Settings.System.DEFAULT_ALARM_ALERT_URI;
				this.saveSettings(savedState);
			} else {
				alarmUri = Uri.parse(savedUri);
			}
		}
		
		enableClick = savedState.getBoolean(KEY_CLICK, DEFAULT_ENABLE_CLICK);
		enableVibrate = savedState.getBoolean(KEY_VIBRATE, DEFAULT_ENABLE_VIBRATE);
	}

	/**
	 * Saves options
	 * @see com.japtar.chessclock.models.SaveStateModel#saveSettings(android.content.SharedPreferences)
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
		savedTimeLimit.saveTime(saveEditor, KEY_TIME_LIMIT);
		savedDelayTime.saveTime(saveEditor, KEY_DELAY_TIME);
		
		saveEditor.putString(KEY_ALARM, alarmUri.toString());
		
		saveEditor.putBoolean(KEY_CLICK, enableClick);
		saveEditor.putBoolean(KEY_VIBRATE, enableVibrate);
		
		// Write it in!
		saveEditor.commit();
	}
}
