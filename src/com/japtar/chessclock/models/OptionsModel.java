/**
 * Chess Clock App
 * https://github.com/japtar10101/Chess-Clock-App
 * 
 * The MIT License
 * 
 * Copyright (c) 2011 Taro Omiya
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.japtar.chessclock.models;

import com.japtar.chessclock.enums.DelayMode;

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
	// == White Player's time ==
	/** The saved key value for time limit */
	public static final String KEY_WHITE_TIME_LIMIT = "timeLimit";
	/** The saved key value for delay time */
	public static final String KEY_WHITE_DELAY_TIME = "delayTime";

	// == Black Player's time ==
	/** The saved key value for time limit */
	public static final String KEY_BLACK_TIME_LIMIT = "timeLimitBlack";
	/** The saved key value for delay time */
	public static final String KEY_BLACK_DELAY_TIME = "delayTimeBlack";
	
	// == Advanced Options ==
	/** The saved key value for enabling handicap */
	public static final String KEY_HANDICAP = "enableHandicap";
	/** The saved key value for the list of delay modes */
	public static final String KEY_DELAY_MODE = "delayMode";
	
	// == Feedback ==
	/** The saved key value for alarm */
	public static final String KEY_ALARM = "alarm";
	/** The saved key value for enabling click sound */
	public static final String KEY_CLICK = "click";
	/** The saved key value for vibrating */
	public static final String KEY_VIBRATE = "vibrate";
	/** The saved key value for displaying the move count */
	public static final String KEY_MOVE_COUNT = "moveCount";
	
	// == Default values ==
	// == Player's time ==
	/** The default time limit */
	public static final int DEFAULT_TIME_LIMIT = 300;
	/** The default delay time */
	public static final byte DEFAULT_DELAY_TIME = 2;

	// == Feedback ==
	/** The default value for {@link #enableClick} */
	public static final boolean DEFAULT_ENABLE_CLICK = true;
	/** The default value for {@link #enableVibrate} */
	public static final boolean DEFAULT_ENABLE_VIBRATE = true;
	/** The default value for {@link #displayMoveCount} */
	public static final boolean DEFAULT_DISPLAY_MOVE_COUNT = true;
	
	// == Advanced Options ==
	/** The default value for {@link #enableHandicap} */
	public static final boolean DEFAULT_ENABLE_HANDICAP = false;
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == White player's time ==
	/** The white player's time limit */
	public final TimeModel savedWhiteTimeLimit = new TimeModel(DEFAULT_TIME_LIMIT);
	/** The white player's delay time */
	public final TimeModel savedWhiteDelayTime = new TimeModel(DEFAULT_DELAY_TIME);
	
	// == Black player's time ==
	/** The black player's time limit */
	public final TimeModel savedBlackTimeLimit = new TimeModel(DEFAULT_TIME_LIMIT);
	/** The black player's delay time */
	public final TimeModel savedBlackDelayTime = new TimeModel(DEFAULT_DELAY_TIME);
	
	// == Alarm ==
	/** The stored alarm */
	public Uri alarmUri = null;
	
	// == Feedback ==
	/** If true, make a sound when pressing a game button */
	public boolean enableClick = DEFAULT_ENABLE_CLICK;
	/** If true, vibrates when clicking any button */
	public boolean enableVibrate = DEFAULT_ENABLE_VIBRATE;
	/** If true, displays the move count for each player */
	public boolean displayMoveCount = DEFAULT_DISPLAY_MOVE_COUNT;
	
	// == Advanced Options ==
	/** If true, the white and black player's options will be used respectively.
	 * Otherwise, only the white player's options will be used for both players. */
	public boolean enableHandicap = DEFAULT_ENABLE_HANDICAP;
	/** The delay mode for this game */
	public byte delayMode = DelayMode.BASIC;
	
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
		
		// Recall the white player attributes to the bundle
		savedWhiteTimeLimit.recallTime(savedState, KEY_WHITE_TIME_LIMIT,
				DEFAULT_TIME_LIMIT);
		savedWhiteDelayTime.recallTime(savedState, KEY_WHITE_DELAY_TIME,
				DEFAULT_DELAY_TIME);
		
		// Recall the black player attributes to the bundle
		savedBlackTimeLimit.recallTime(savedState, KEY_BLACK_TIME_LIMIT,
				DEFAULT_TIME_LIMIT);
		savedBlackDelayTime.recallTime(savedState, KEY_BLACK_DELAY_TIME,
				DEFAULT_DELAY_TIME);
		
		// Recall the alarm
		String savedString = savedState.getString(KEY_ALARM, null);
		alarmUri = null;
		if((savedString != null) && (savedString.length() > 0)) {
			if(savedString.equals("defaultRingtone")) {
				alarmUri = Settings.System.DEFAULT_ALARM_ALERT_URI;
				this.saveSettings(savedState);
			} else {
				alarmUri = Uri.parse(savedString);
			}
		}
		
		// Recall feedback-related conditionals
		enableClick = savedState.getBoolean(KEY_CLICK, DEFAULT_ENABLE_CLICK);
		enableVibrate = savedState.getBoolean(KEY_VIBRATE,
				DEFAULT_ENABLE_VIBRATE);
		displayMoveCount = savedState.getBoolean(KEY_MOVE_COUNT,
				DEFAULT_DISPLAY_MOVE_COUNT);
		
		// Recall advanced options
		enableHandicap = savedState.getBoolean(KEY_HANDICAP,
				DEFAULT_ENABLE_HANDICAP);
		
		// Recall the game mode
		savedString = savedState.getString(KEY_DELAY_MODE, DelayMode.STRING_BASIC);
		if(savedString.equals(DelayMode.STRING_BRONSTEIN)) {
			delayMode = DelayMode.BRONSTEIN;
		} else if(savedString.equals(DelayMode.STRING_FISCHER)) {
			delayMode = DelayMode.FISCHER;
		} else if(savedString.equals(DelayMode.STRING_FISCHER_AFTER)) {
			delayMode = DelayMode.FISCHER_AFTER;
		} else if(savedString.equals(DelayMode.STRING_HOUR_GLASS)) {
			delayMode = DelayMode.HOUR_GLASS;
		} else {
			delayMode = DelayMode.BASIC;
		}
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
		
		// Save the white player attributes to the bundle
		savedWhiteTimeLimit.saveTime(saveEditor, KEY_WHITE_TIME_LIMIT);
		savedWhiteDelayTime.saveTime(saveEditor, KEY_WHITE_DELAY_TIME);
		
		// Save the black player attributes to the bundle
		savedBlackTimeLimit.saveTime(saveEditor, KEY_BLACK_TIME_LIMIT);
		savedBlackDelayTime.saveTime(saveEditor, KEY_BLACK_DELAY_TIME);
		
		// Save the alarm
		saveEditor.putString(KEY_ALARM, alarmUri.toString());
		
		// Save feedback-related conditionals
		saveEditor.putBoolean(KEY_CLICK, enableClick);
		saveEditor.putBoolean(KEY_VIBRATE, enableVibrate);
		saveEditor.putBoolean(KEY_MOVE_COUNT, displayMoveCount);
		
		// Save advanced options
		saveEditor.putBoolean(KEY_HANDICAP, enableHandicap);
		switch(delayMode) {
			case DelayMode.FISCHER:
				saveEditor.putString(KEY_DELAY_MODE, DelayMode.STRING_FISCHER);
				break;
			case DelayMode.FISCHER_AFTER:
				saveEditor.putString(KEY_DELAY_MODE, DelayMode.STRING_FISCHER_AFTER);
				break;
			case DelayMode.BRONSTEIN:
				saveEditor.putString(KEY_DELAY_MODE, DelayMode.STRING_BRONSTEIN);
				break;
			case DelayMode.HOUR_GLASS:
				saveEditor.putString(KEY_DELAY_MODE, DelayMode.STRING_HOUR_GLASS);
				break;
			case DelayMode.BASIC:
			default:
				saveEditor.putString(KEY_DELAY_MODE, DelayMode.STRING_BASIC);
				break;
		}
		
		// Write it in!
		saveEditor.commit();
	}
}
