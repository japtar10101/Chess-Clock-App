/**
 * 
 */
package com.app.chessclock.menus;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.chessclock.Global;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;
import com.app.chessclock.enums.TimerCondition;
import com.app.chessclock.models.Time;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class TimersMenu extends ActivityMenu implements OnClickListener {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == Timer ==
	private final Handler mTimer = new Handler();
	private final ChessTask mTask = new ChessTask();
	
	// == Conditionals ==
	/** The current timer's condition */
	private TimerCondition mCondition = TimerCondition.STARTING;
	/** Flag indicating whose turn it is */
	private boolean mLeftPlayersTurn = false;
	
	// == Times ==
	/** Left player's time */
	private final Time mLeftPlayersTime = new Time();
	/** Right player's time */
	private final Time mRightPlayersTime = new Time();
	/** Time of delay */
	private final Time mDelayTime = new Time();
	
	// == Buttons ==
	/** Left player's button */
	private Button mLeftButton = null;
	/** Right player's button */
	private Button mRightButton = null;
	
	// == Labels ==
	/** Label indicating delay */
	private TextView mDelayLabel = null;
	
	// == Dialog ==
	// FIXME: create a start dialog, which disappears automatically
	// FIXME: create a generic pause dialog
	// FIXME: create a menu-button pause dialog
	// FIXME: create a times-up dialog
	
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
	public void setupLayout() {
		// First, setup the UI
		mParentActivity.setContentView(R.layout.main);

		// Determine the condition to begin this game at
		if(Global.OPTIONS.isPaused) {
			mCondition = TimerCondition.PAUSE_WITHOUT_MENU;
		} else {
			this.reset();
		}
	}

	/**
	 * Pauses the timer, unless the time is up.
	 * @see com.app.chessclock.menus.ActivityMenu#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitLayout() {
		// Set the option's state
		switch(mCondition) {
			case TIMES_UP:
			case STARTING:
				Global.OPTIONS.isPaused = false;
				break;
			default:
				Global.OPTIONS.isPaused = true;
				mCondition = TimerCondition.PAUSE_WITHOUT_MENU;
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
		mCondition = TimerCondition.RUNNING;
		mLeftPlayersTurn = v.equals(mRightButton);
		
		// Enable only one button
		mLeftButton.setEnabled(mLeftPlayersTurn);
		mRightButton.setEnabled(!mLeftPlayersTurn);
		
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
		if(mCondition == TimerCondition.TIMES_UP) {
			return false;
		} else {
			return true;
		}
	}
	
	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	/**
	 * Resets this menu.
	 */
	private void reset() {
		// Set the condition to starting
		mCondition = TimerCondition.STARTING;
		
		// Grab the buttons
		mLeftButton = (Button)mParentActivity.findViewById(R.id.buttonLeftTime);
		mRightButton = (Button)mParentActivity.findViewById(R.id.buttonRightTime);
		
		// Set the buttons click behavior to this class
		mLeftButton.setOnClickListener(this);
		mRightButton.setOnClickListener(this);
		
		// Grab the labels
		mDelayLabel = (TextView)mParentActivity.findViewById(R.id.labelDelay);

		// Reset the time
		mLeftPlayersTime.setTime(Global.OPTIONS.getTimeLimit());
		mRightPlayersTime.setTime(Global.OPTIONS.getTimeLimit());
		mDelayTime.setTime(Global.OPTIONS.getDelayTime());
		
		// Enable both buttons
		mLeftButton.setEnabled(true);
		mRightButton.setEnabled(true);
		
		// Update their text
		mLeftButton.setText(mLeftPlayersTime.toString());
		mRightButton.setText(mRightPlayersTime.toString());
		
		// Hide the delay label
		mDelayLabel.setVisibility(View.INVISIBLE);
		
		// FIXME: show the delay dialog, indicating to push either button
	}
	
	/**
	 * Indicate the time is up
	 */
	private void timesUp() {
		// Set the condition to time up
		mCondition = TimerCondition.TIMES_UP;
		
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
			Time updateTime = mRightPlayersTime;
			if(mLeftPlayersTurn) {
				updateTime = mLeftPlayersTime;
			}
			
			// Decrement the time that many seconds
			final int timeSpanSeconds = (int) (timeSpanMilliseconds / 1000);
			for(int second = 0; second < timeSpanSeconds; ++second) {
				// First attempt to decrement the delay time
				if(!mDelayTime.decrementASecond()) {
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
			if(mCondition == TimerCondition.RUNNING) {
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
			mDelayTime.setTime(Global.OPTIONS.getDelayTime());
			updateLabels();
		}
		
		/* ===========================================================
		 * Private/Protected Methods
		 * =========================================================== */
		private void updateLabels() {
			// Update the corresponding button
			if(mLeftPlayersTurn) {
				mLeftButton.setText(mLeftPlayersTime.toString());
			} else {
				mRightButton.setText(mRightPlayersTime.toString());
			}
			
			// Update the delay time
			if(mDelayTime.isTimeZero()) {
				// Set the label invisible, if at 0
				mDelayLabel.setVisibility(View.INVISIBLE);
			} else {
				// Generate the string to display
				String labelText = mParentActivity.getString(
						R.string.delayLabelText);
				labelText += mDelayTime.toString();
				
				// Set the label visible, and update the text
				mDelayLabel.setVisibility(View.VISIBLE);
				mDelayLabel.setText(labelText);
			}
		}
	}
}
