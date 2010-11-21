/**
 * Package of menus
 */
package com.japtar.chessclock.menus;

import android.os.Handler;
import android.os.SystemClock;

import com.japtar.chessclock.Global;
import com.japtar.chessclock.enums.TimerCondition;

/**
 * Helper class for TimersMenu.  Progressively decreases a player's time.
 * @see TimersMenu
 * @author japtar10101
 */
public class DecrementTimerTask implements Runnable {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The handler running this task */
	private final Handler mTimer;
	/** The menu using this task */
	private final TimersMenu mMenu;
	/** The time this task has begun */
	private long mStartTime = 0L;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * Creates a new runnable that re-runs handler <b>timer</p>
	 * @param menu the menu running this task
	 * @param timer the handler to re-run
	 * @see android.os.Handler
	 */
	public DecrementTimerTask(final TimersMenu menu, final Handler timer) {
		// Setup all the member variables
		mMenu = menu;
		mTimer = timer;
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * Decrement the player's time, and update the GUI
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// Figure out the amount of time delayed since last called
		final long timeSpanMilliseconds =
			SystemClock.uptimeMillis() - mStartTime;
		
		// Update the start time immediately
		mStartTime = SystemClock.uptimeMillis();
		
		// Decrement time
		final int timeSpanSeconds = (int) (timeSpanMilliseconds / 1000);
		if(Global.GAME_STATE.decrementTime(timeSpanSeconds)) {
			// If a player runs out of time, indicate the time's up
			mMenu.timesUp();
		} else if(Global.GAME_STATE.timerCondition == TimerCondition.RUNNING) {
			// Call this task again, if condition is still running
			mTimer.postDelayed(this, 1000);
		}
		
		// Update the button's text
		mMenu.updateButtonAndLabelText();
		
	}
	
	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Updates {@link mStartTime} to the current time
	 */
	public void reset() {
		mStartTime = SystemClock.uptimeMillis();
	}
}
