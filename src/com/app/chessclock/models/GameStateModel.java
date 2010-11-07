/**
 * Helper classes
 */
package com.app.chessclock.models;

import java.security.InvalidParameterException;

import android.os.Bundle;

import com.app.chessclock.enums.TimerCondition;

/**
 * FIXME: finish this model
 * @author japtar10101
 */
public class GameStateModel {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	/** The saved key value for who's turn it is */
	public static final String KEY_LEFT_PLAYERS_TURN = "leftPlayersTurn";
	/** The saved key value for the timer condition */
	public static final String KEY_TIMER_CONDITION = "timerCondition";
	
	/** The saved key value for left player's time (minutes) */
	public static final String KEY_LEFT_PLAYERS_MINUTES = "leftPlayersMinutes";
	/** The saved key value for left player's (seconds) */
	public static final String KEY_LEFT_PLAYERS_SECONDS = "leftPlayersSeconds";
	
	/** The saved key value for left player's time (minutes) */
	public static final String KEY_RIGHT_PLAYERS_MINUTES = "rightPlayersMinutes";
	/** The saved key value for left player's time (seconds) */
	public static final String KEY_RIGHT_PLAYERS_SECONDS = "rightPlayersSeconds";
	
	/** The saved key value for delay time (minutes) */
	public static final String KEY_DELAY_TIME_MINUTES = "gameDelayMinutes";
	/** The saved key value for delay time (seconds) */
	public static final String KEY_DELAY_TIME_SECONDS = "gameDelaySeconds";
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The saved state */
	private Bundle mSavedState = null;
	
	// == Conditionals ==
	/** Flag indicating whose turn it is */
	public boolean leftPlayersTurn = false;
	/** The current timer's condition */
	public TimerCondition timerCondition = TimerCondition.STARTING;
	
	// == Times ==
	/** Left player's time */
	private final TimeModel mLeftPlayersTime = new TimeModel();
	/** Right player's time */
	private final TimeModel mRightPlayersTime = new TimeModel();
	/** Time of delay */
	private final TimeModel mDelayTime = new TimeModel();
	
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
			mSavedState.putInt(KEY_LEFT_PLAYERS_MINUTES, mLeftPlayersTime.getMinutes());
			mSavedState.putInt(KEY_LEFT_PLAYERS_SECONDS, mLeftPlayersTime.getSeconds());
			
			mSavedState.putInt(KEY_RIGHT_PLAYERS_MINUTES, mRightPlayersTime.getMinutes());
			mSavedState.putInt(KEY_RIGHT_PLAYERS_SECONDS, mRightPlayersTime.getSeconds());
			
			mSavedState.putInt(KEY_DELAY_TIME_MINUTES, mDelayTime.getMinutes());
			mSavedState.putInt(KEY_DELAY_TIME_SECONDS, mDelayTime.getSeconds());
			
			mSavedState.putBoolean(KEY_LEFT_PLAYERS_TURN, leftPlayersTurn);
			
			mSavedState.putInt(KEY_TIMER_CONDITION, timerCondition.ordinal());
			
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
			mLeftPlayersTime.setMinutes(mSavedState.getInt(
					KEY_LEFT_PLAYERS_MINUTES));
			mLeftPlayersTime.setSeconds(mSavedState.getInt(
					KEY_LEFT_PLAYERS_SECONDS));

			mRightPlayersTime.setMinutes(mSavedState.getInt(
					KEY_RIGHT_PLAYERS_MINUTES));
			mRightPlayersTime.setSeconds(mSavedState.getInt(
					KEY_RIGHT_PLAYERS_SECONDS));

			mDelayTime.setMinutes(mSavedState.getInt(KEY_DELAY_TIME_MINUTES));
			mDelayTime.setSeconds(mSavedState.getInt(KEY_DELAY_TIME_SECONDS));
			
			leftPlayersTurn = mSavedState.getBoolean(KEY_LEFT_PLAYERS_TURN);
			
			final int ordinal = mSavedState.getInt(KEY_TIMER_CONDITION);
			final TimerCondition[] conditions = TimerCondition.values();
			if(ordinal >= conditions.length) {
				timerCondition = TimerCondition.STARTING;
			} else {
				timerCondition = conditions[ordinal];
			}
		}
	}
}
