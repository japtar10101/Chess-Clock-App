/**
 * Helper classes
 */
package com.japtar.chessclock.models;

import java.security.InvalidParameterException;

import android.content.SharedPreferences;

import com.japtar.chessclock.Global;
import com.japtar.chessclock.enums.TimerCondition;

/**
 * Model indicating the game's current (or paused) conditions.
 * @author japtar10101
 */
public class GameStateModel implements SaveStateModel {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	// == Stored key values ==
	// == Conditionals ==
	/** The saved key value for who's turn it is */
	public static final String KEY_LEFT_PLAYERS_TURN = "leftPlayersTurn";
	/** The saved key value for whether the left player is white or not */
	public static final String KEY_LEFT_IS_WHITE = "leftIsWhite";
	/** The saved key value for the timer condition */
	public static final String KEY_TIMER_CONDITION = "timerCondition";
	
	// == Player's time ==
	/** The saved key value for left player's time */
	public static final String KEY_LEFT_PLAYER = "leftPlayersTime";
	/** The saved key value for right player's time */
	public static final String KEY_RIGHT_PLAYER = "rightPlayersTime";
	
	// == Player's delay time ==
	/** The saved key value for delay time */
	public static final String KEY_LEFT_DELAY_TIME = "gameDelayTime";
	/** The saved key value for delay time */
	public static final String KEY_RIGHT_DELAY_TIME = "rightPlayerDelayTime";
	
	// == Number of Player's moves ==
	/** The saved key value for number of moves */
	public static final String KEY_LEFT_NUM_MOVES = "leftNumMoves";
	/** The saved key value for number of moves */
	public static final String KEY_RIGHT_NUM_MOVES = "rightNumMoves";
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** prepend string for delay time */
	private String mDelayPrependString = null;
	
	// == Conditionals ==
	/** Flag indicating whose turn it is */
	public boolean leftPlayersTurn = false;
	/** Flag indicating whose turn it is */
	public boolean leftIsWhite = false;
	/** The current timer's condition */
	public byte timerCondition = TimerCondition.STARTING;
	
	// == Player's time ==
	/** Left player's time */
	private final TimeModel mLeftPlayersTime = new TimeModel();
	/** Right player's time */
	private final TimeModel mRightPlayersTime = new TimeModel();
	
	// == Player's delay time ==
	/** Time of delay */
	private final TimeModel mLeftPlayerDelayTime = new TimeModel();
	/** Time of delay */
	private final TimeModel mRightPlayerDelayTime = new TimeModel();
	
	// == Player's moves ==
	/** Number of moves the left player made */
	public int numLeftPlayerMoves = 0;
	/** Number of moves the right player made */
	public int numRightPlayerMoves = 0;
	
	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	/**
	 * Saves the current settings to a SharedPreferences
	 * @param saveState the SharedPreferences to save this app's options
	 * @see SharedPreferences
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
		
		// First, save all the time variables
		mLeftPlayersTime.saveTime(saveEditor, KEY_LEFT_PLAYER);
		mRightPlayersTime.saveTime(saveEditor, KEY_RIGHT_PLAYER);
		mLeftPlayerDelayTime.saveTime(saveEditor, KEY_LEFT_DELAY_TIME);
		mRightPlayerDelayTime.saveTime(saveEditor, KEY_RIGHT_DELAY_TIME);
		
		// Who's turn it is
		saveEditor.putBoolean(KEY_LEFT_PLAYERS_TURN, leftPlayersTurn);
		saveEditor.putBoolean(KEY_LEFT_IS_WHITE, leftIsWhite);
		
		// The value of timerCondition
		saveEditor.putInt(KEY_TIMER_CONDITION, timerCondition);
		
		// Number of moves for each player
		saveEditor.putInt(KEY_LEFT_NUM_MOVES, numLeftPlayerMoves);
		saveEditor.putInt(KEY_RIGHT_NUM_MOVES, numRightPlayerMoves);
		
		// Write it in!
		saveEditor.commit();
	}
	
	/**
	 * Saves the current settings to a SharedPreferences
	 * @param savedState the SharedPreferences to save this app's options
	 * @see SharedPreferences
	 * @param savedState shared Preference to save to
	 */
	@Override
	public void recallSettings(final SharedPreferences savedState) throws
			InvalidParameterException, IllegalArgumentException {
		
		// Make sure the parameter isn't null
		if(savedState == null) {
			// If so, complain
			throw new IllegalArgumentException("savedState cannot be null");
		}
		
		// Recall the attributes from the preference
		// First, the time variables
		mLeftPlayersTime.recallTime(savedState, KEY_LEFT_PLAYER,
				OptionsModel.DEFAULT_TIME_LIMIT);
		mRightPlayersTime.recallTime(savedState, KEY_RIGHT_PLAYER,
				OptionsModel.DEFAULT_TIME_LIMIT);
		mLeftPlayerDelayTime.recallTime(savedState, KEY_LEFT_DELAY_TIME,
				OptionsModel.DEFAULT_DELAY_TIME);
		
		// For backward compatibility, if this value is not recalled,
		// set it the same delay time as the left player
		mRightPlayerDelayTime.recallTime(savedState, KEY_RIGHT_DELAY_TIME,
				TimeModel.totalSeconds(mLeftPlayerDelayTime));
		
		// Who's turn it is...
		leftPlayersTurn = savedState.getBoolean(KEY_LEFT_PLAYERS_TURN, true);
		leftIsWhite = savedState.getBoolean(KEY_LEFT_IS_WHITE, true);
		
		// Number of moves for each player
		numLeftPlayerMoves = savedState.getInt(KEY_LEFT_NUM_MOVES, 0);
		numRightPlayerMoves = savedState.getInt(KEY_RIGHT_NUM_MOVES, 0);
		
		// Lastly, the game's state
		timerCondition = TimeModel.intToByte(
				savedState.getInt(KEY_TIMER_CONDITION, 0));
		if(timerCondition >= TimerCondition.NUM_CONDITIONS) {
			timerCondition = TimerCondition.STARTING;
		}
	}
	
	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Decrements time.
	 * @param numSeconds number of seconds to decrement
	 * @return true if either player's time is up.
	 */
	public boolean decrementTime(final int numSeconds) {
		boolean toReturn = false;
		
		// Figure out which time to decrement
		TimeModel updateTime = mRightPlayersTime;
		if(leftPlayersTurn) {
			updateTime = mLeftPlayersTime;
		}
		
		// Decrement time from delay or player
		for(int second = 0; second < numSeconds; ++second) {
			
			// First attempt to decrement the delay time
			if(!mLeftPlayerDelayTime.decrementASecond()) {
				
				// If failed, try decrementing the player's time
				if(!updateTime.decrementASecond()) {
					
					// If that failed, times up!
					toReturn = true;
					break;
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * Resets the internal game time to option's settings
	 */
	public void resetTime() {
		// Revert all the time to Option's settings
		mLeftPlayersTime.setTime(Global.OPTIONS.savedWhiteTimeLimit);
		mRightPlayersTime.setTime(Global.OPTIONS.savedWhiteTimeLimit);
		this.resetDelay();
	}
	
	/**
	 * Resets the internal delay time to option's settings
	 */
	public void resetDelay() {
		mLeftPlayerDelayTime.setTime(Global.OPTIONS.savedWhiteDelayTime);
	}
	
	/**
	 * @return left player's current time, in text
	 */
	public String leftPlayerTime() {
		return mLeftPlayersTime.toString();
	}
	
	/**
	 * @return right player's current time, in text
	 */
	public String rightPlayerTime() {
		return mRightPlayersTime.toString();
	}
	
	/**
	 * @return If the delay time is zero, returns null.
	 * Otherwise, returns the current delay time, in text.
	 */
	public String delayTime() {
		// Check if the delay time is zero
		if(mLeftPlayerDelayTime.isTimeZero()) {
			
			// If so, return null
			return null;
		}
		
		// Calculate the total seconds to delay
		final Integer seconds = mLeftPlayerDelayTime.getMinutes() * 60 +
			mLeftPlayerDelayTime.getSeconds();
		
		// Prepend the string, if available
		if(mDelayPrependString != null) {
			return mDelayPrependString + ' ' + seconds.toString();
		} else {
			return seconds.toString();
		}
	}
	
	/**
	 * @return A default delay label string
	 */
	public String defaultDelayLabelString() {
		// Prepend the string, if available
		if(mDelayPrependString != null) {
			return mDelayPrependString + " 0";
		} else {
			return "0";
		}
	}
	
	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param prepend sets {@link mDelayPrependString}
	 */
	public void setDelayPrependString(final String prepend) {
		mDelayPrependString = prepend;
	}
}
