/**
 * 
 */
package com.app.chessclock.menus;

import java.util.Timer;

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
	private final Timer mTimeTracker = new Timer();
	
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
	private final Button mLeftButton;
	/** Right player's button */
	private final Button mRightButton;
	
	// == Labels ==
	/** Label indicating delay */
	private final TextView mDelayLabel;
	// FIXME: add a label indicating to click anywhere
	
	// == Dialog ==
	// FIXME: create a pause dialog
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see ActivityMenu#ActivityLayout(MainActivity)
	 */
	public TimersMenu(final MainActivity parent) {
		super(parent);
		
		// Grab the buttons
		mLeftButton = (Button)mParentActivity.findViewById(R.id.buttonLeftTime);
		mRightButton = (Button)mParentActivity.findViewById(R.id.buttonRightTime);
		
		// Grab the labels
		mDelayLabel = (TextView)mParentActivity.findViewById(R.id.labelDelay);
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
		if(mCondition == TimerCondition.TIMES_UP) {
			Global.OPTIONS.isPaused = false;
		} else {
			Global.OPTIONS.isPaused = true;
			mCondition = TimerCondition.PAUSE_WITHOUT_MENU;
		}
	}


	/**
	 * TODO: add a description
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(final View v) {
		// Check to make sure which button this view belongs to
		if(v.equals(mLeftButton)) {
			// Disable the left button, enable the right button
			mLeftButton.setEnabled(false);
			mRightButton.setEnabled(true);
			
			// TODO: Switch the player role
			
			// TODO: restart the timer
		} else {
			// Disable the right button, enable the left button
			mLeftButton.setEnabled(true);
			mRightButton.setEnabled(false);
			
			// TODO: Switch the player role
			
			// TODO: restart the timer
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
		
		// TODO: show the delay dialog, indicating to push either button
	}
}
