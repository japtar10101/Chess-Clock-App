/**
 * 
 */
package com.app.chessclock.menus;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.chessclock.Global;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;
import com.app.chessclock.enums.TimerCondition;
import com.app.chessclock.models.TimeModel;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class TimersMenu extends ActivityMenu implements OnClickListener {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == Conditionals ==
	/** Flag indicating whose turn it is */
	private static boolean msLeftPlayersTurn = false;
	
	// == Times ==
	/** Left player's time */
	private static final TimeModel msLeftPlayersTime = new TimeModel();
	/** Right player's time */
	private static final TimeModel msRightPlayersTime = new TimeModel();
	/** Time of delay */
	private static final TimeModel msDelayTime = new TimeModel();
	
	// == Timer ==
	private final Handler mTimer = new Handler();
	private final ChessTask mTask = new ChessTask();
	
	// == Buttons ==
	/** Left player's button */
	private Button mLeftButton = null;
	/** Right player's button */
	private Button mRightButton = null;
	/** Resume-after-pause button */
	private Button mResumeButton = null;
	/** New game button */
	private Button mNewGameButton = null;
	
	// == Labels ==
	/** Label indicating delay */
	private TextView mDelayLabel = null;
	
	// == Dialog ==
	/** A pause screen, generally left invisible */
	private RelativeLayout mPauseLayout = null;
	/** A "Times Up!" screen, generally left invisible */
	private RelativeLayout mTimesUpLayout = null;
	// FIXME: create a start dialog, which disappears automatically
	
	// == Sound ==
	// FIXME: add a sound
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see ActivityMenu#ActivityLayout(MainActivity)
	 */
	public TimersMenu(final MainActivity parent) {
		super(parent);
	}

	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * Resumes the timer if paused, or starts it over.
	 * @see com.app.chessclock.menus.ActivityMenu#setupLayout(android.app.Activity)
	 */
	@Override
	public void setupMenu() {
		// First, setup the UI
		mParentActivity.setContentView(R.layout.main);

		// Grab the buttons
		mLeftButton = (Button)mParentActivity.findViewById(R.id.buttonLeftTime);
		mRightButton = (Button)mParentActivity.findViewById(R.id.buttonRightTime);
		
		// Set the buttons click behavior to this class
		mLeftButton.setOnClickListener(this);
		mRightButton.setOnClickListener(this);
		
		// Grab the labels
		mDelayLabel = (TextView)mParentActivity.findViewById(R.id.labelDelay);
		
		// Determine the condition to begin this game at
		// FIXME: we need a new model retaining the game's current conditions,
		// and save that in options
		Global.OPTIONS.timerCondition = TimerCondition.PAUSE_WITHOUT_MENU;
		if(Global.OPTIONS.timerCondition != TimerCondition.PAUSE_WITHOUT_MENU) {
			Global.OPTIONS.timerCondition = TimerCondition.STARTING;
			
			// Reset the time
			msLeftPlayersTime.setTime(Global.OPTIONS.savedTimeLimit);
			msRightPlayersTime.setTime(Global.OPTIONS.savedTimeLimit);
			msDelayTime.setTime(Global.OPTIONS.savedDelayTime);
			
			// Enable both buttons
			mLeftButton.setEnabled(true);
			mRightButton.setEnabled(true);
		}
		
		// Update their text
		mLeftButton.setText(msLeftPlayersTime.toString());
		mRightButton.setText(msRightPlayersTime.toString());
		
		// Hide the delay label
		mDelayLabel.setVisibility(View.INVISIBLE);
		
		// FIXME: show the delay dialog, indicating to push either button
	}

	/**
	 * Pauses the timer, unless the time is up.
	 * @see com.app.chessclock.menus.ActivityMenu#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitMenu() {
		// Set the option's state
		switch(Global.OPTIONS.timerCondition) {
			case TIMES_UP:
			case STARTING:
				Global.OPTIONS.timerCondition = TimerCondition.STARTING;
				break;
			default:
				Global.OPTIONS.timerCondition = TimerCondition.PAUSE_WITHOUT_MENU;
		}
	}


	/**
	 * Updates the player's turns, based on the button clicked
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(final View v) {
		// Stop the timer
		mTimer.removeCallbacks(mTask);

		// Update the condition and current player
		Global.OPTIONS.timerCondition = TimerCondition.RUNNING;
		msLeftPlayersTurn = v.equals(mRightButton);
		
		// Enable only one button
		mLeftButton.setEnabled(msLeftPlayersTurn);
		mRightButton.setEnabled(!msLeftPlayersTurn);
		
		// Restart the timer
		mTask.reset();
		mTimer.postDelayed(mTask, 1000);
	}
	
	/**
	 * @return true unless time's up
	 * @see com.app.chessclock.menus.ActivityMenu#enableMenuButton()
	 */
	@Override
	public boolean enableMenuButton() {
		/* TODO: uncomment when the dialogs are up and working
		if(mCondition == TimerCondition.TIMES_UP) {
			return false;
		} else {
			return true;
		}
		*/
		return true;
	}
	
	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	/**
	 * Indicate the time is up
	 */
	private void timesUp() {
		// Set the condition to time up
		Global.OPTIONS.timerCondition = TimerCondition.TIMES_UP;
		
		// Disable both buttons
		mLeftButton.setEnabled(false);
		mRightButton.setEnabled(false);
		
		// Hide the delay label
		mDelayLabel.setVisibility(View.INVISIBLE);
		
		// TODO: show the delay dialog, indicating to start over,
		// or go to options
	}
	
	/* ===========================================================
	 * Private/Protected Class
	 * =========================================================== */
	private class ChessTask implements Runnable {
		/* ===========================================================
		 * Members
		 * =========================================================== */
		/** The time this task has begun */
		private long mStartTime = 0L;
		
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
			
			// Figure out which time to decrement
			TimeModel updateTime = msRightPlayersTime;
			if(msLeftPlayersTurn) {
				updateTime = msLeftPlayersTime;
			}
			
			// Decrement the time that many seconds
			final int timeSpanSeconds = (int) (timeSpanMilliseconds / 1000);
			for(int second = 0; second < timeSpanSeconds; ++second) {
				// First attempt to decrement the delay time
				if(!msDelayTime.decrementASecond()) {
					// If failed, try decrementing the player's time
					if(!updateTime.decrementASecond()) {
						// If that failed, times up!
						timesUp();
					}
				}
			}
			
			// Update the button's text
			updateLabels();
			
			// Call this task again, if condition is still running
			if(Global.OPTIONS.timerCondition == TimerCondition.RUNNING) {
				mTimer.postDelayed(this, 1000);
			}
		}
		
		/* ===========================================================
		 * Public Methods
		 * =========================================================== */
		/**
		 * Updates {@link mStartTime} to the current time
		 */
		public void reset() {
			mStartTime = SystemClock.uptimeMillis();
			msDelayTime.setTime(Global.OPTIONS.savedDelayTime);
			updateLabels();
		}
		
		/* ===========================================================
		 * Private/Protected Methods
		 * =========================================================== */
		private void updateLabels() {
			// Update the corresponding button
			if(msLeftPlayersTurn) {
				mLeftButton.setText(msLeftPlayersTime.toString());
			} else {
				mRightButton.setText(msRightPlayersTime.toString());
			}
			
			// Update the delay time
			if(msDelayTime.isTimeZero()) {
				// Set the label invisible, if at 0
				mDelayLabel.setVisibility(View.INVISIBLE);
			} else {
				// Generate the string to display
				String labelText = mParentActivity.getString(
						R.string.delayLabelText);
				labelText += msDelayTime.toString();
				
				// Set the label visible, and update the text
				mDelayLabel.setVisibility(View.VISIBLE);
				mDelayLabel.setText(labelText);
			}
		}
	}
}
