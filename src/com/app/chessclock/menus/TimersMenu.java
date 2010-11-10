/**
 * Package of menus
 */
package com.app.chessclock.menus;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chessclock.Global;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;
import com.app.chessclock.enums.TimerCondition;

/**
 * Menu for Timer
 * @author japtar10101
 */
public class TimersMenu  implements OnClickListener, ActivityMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The main activities class */
	protected MainActivity mParentActivity;
	
	// == Timer ==
	/** The handler running {@link mTask} */
	private final Handler mTimer;
	/** Task that decrements the timer */
	private final DecrementTimerTask mTask;
	
	// == Buttons ==
	/** Left player's button */
	private Button mLeftButton = null;
	/** Right player's button */
	private Button mRightButton = null;
	/** The All-purpose (mainly for pausing the game) button */
	private Button mPauseButton = null;
	
	// == Labels ==
	/** Label indicating delay */
	private TextView mDelayLabel = null;
	
	// == Dialog ==
	/** A pause screen, generally left invisible */
	private RelativeLayout mPauseLayout = null;
	/** A "Times Up!" screen, generally left invisible */
	private RelativeLayout mTimesUpLayout = null;
	
	// == Sound ==
	// FIXME: add a sound
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @param parent the menu's parent activity
	 */
	public TimersMenu(final MainActivity parent) {
		// Setup activity
		mParentActivity = parent;
		
		// Setup the timer-related stuff
		mTimer = new Handler();
		mTask = new DecrementTimerTask(this, mTimer);
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

		// Grab the label
		mDelayLabel = (TextView)mParentActivity.findViewById(R.id.labelDelay);
		
		// Grab layouts
		mPauseLayout = (RelativeLayout)
			mParentActivity.findViewById(R.id.layoutPause);
		mTimesUpLayout = (RelativeLayout)
			mParentActivity.findViewById(R.id.layoutTimesUp);
		
		// Grab the buttons
		mLeftButton = (Button)mParentActivity.findViewById(R.id.buttonLeftTime);
		mRightButton = (Button)mParentActivity.findViewById(R.id.buttonRightTime);
		mPauseButton = (Button)mParentActivity.findViewById(R.id.buttonPause);
		
		// Set the buttons click behavior to this class
		mLeftButton.setOnClickListener(this);
		mRightButton.setOnClickListener(this);
		mPauseButton.setOnClickListener(this);
		
		// Determine the condition to begin this game at
		switch(Global.GAME_STATE.timerCondition) {
			case TimerCondition.TIMES_UP:
			case TimerCondition.STARTING:
				this.startup();
				Toast.makeText(mParentActivity,
						"Click either button to start the timer.\n" +
						"Press the menu button to pause.",
						Toast.LENGTH_LONG).show();
				break;
			default:
				this.paused();
				break;
		}
	}

	/**
	 * Pauses the timer, unless the time is up.
	 * @see com.app.chessclock.menus.ActivityMenu#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitMenu() {
		// Set the option's state
		switch(Global.GAME_STATE.timerCondition) {
			case TimerCondition.TIMES_UP:
			case TimerCondition.STARTING:
				Global.GAME_STATE.timerCondition = TimerCondition.STARTING;
				break;
			default:
				Global.GAME_STATE.timerCondition = TimerCondition.PAUSE;
				break;
		}
	}

	/**
	 * Updates the player's turns, based on the button clicked
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(final View v) {
		// Check if the button clicked is the new game button
		if(v.equals(mPauseButton)) {
			switch(Global.GAME_STATE.timerCondition) {
				// If running, pause the game
				case TimerCondition.RUNNING:
					this.paused();
					return;
				// TODO: once options is fixed, go to options screen 
				case TimerCondition.STARTING:
					return;
				// If time's up, restart the game
				case TimerCondition.TIMES_UP:
					this.startup();
					return;
			}
		}
		
		// Stop the handler
		mTimer.removeCallbacks(mTask);
		
		// Set condition to running
		Global.GAME_STATE.timerCondition = TimerCondition.RUNNING;
		
		// Check which button was pressed
		final boolean isLeftPlayersTurn = v.equals(mRightButton);
		if(isLeftPlayersTurn || v.equals(mLeftButton)) {
			// If clicked by right/left player button,
			// update the current player
			Global.GAME_STATE.leftPlayersTurn = isLeftPlayersTurn;
			
			// Reset the delay time
			Global.GAME_STATE.resetDelay();
			
			// Update the Delay label
			this.updateDelayLabel();
		}
		
		// Set both layouts to be invisible
		mPauseLayout.setVisibility(View.INVISIBLE);
		mTimesUpLayout.setVisibility(View.INVISIBLE);
					
		// Enable only one button
		mLeftButton.setEnabled(Global.GAME_STATE.leftPlayersTurn);
		mRightButton.setEnabled(!Global.GAME_STATE.leftPlayersTurn);
		
		// Start the timer
		mTask.reset();
		mTimer.postDelayed(mTask, 1000);
	}
	
	/**
	 * @return true
	 * @see com.app.chessclock.menus.ActivityMenu#enableMenuButton()
	 */
	@Override
	public boolean enableMenuButton() {
		// Show the menu (by returning true)
		return true;
	}
	
	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	/**
	 * Indicate the game just started
	 */
	private void startup() {
		// Set the condition to time up
		Global.GAME_STATE.timerCondition = TimerCondition.STARTING;
		
		// Reset the time
		Global.GAME_STATE.resetTime();
		
		// Update the buttons/labels text
		this.updateButtonAndLabelText();
		
		// Enable both buttons
		mLeftButton.setEnabled(true);
		mRightButton.setEnabled(true);
		
		// Update their text
		mLeftButton.setText(mParentActivity.getString(R.string.playerButtonText));
		mRightButton.setText(mParentActivity.getString(R.string.playerButtonText));
		
		// Set both layouts to be invisible
		mPauseLayout.setVisibility(View.INVISIBLE);
		mTimesUpLayout.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Indicate the game is paused
	 */
	private void paused() {
		// Set the condition to time up
		Global.GAME_STATE.timerCondition = TimerCondition.PAUSE;
		
		// Update the buttons/labels text
		this.updateButtonAndLabelText();
		
		// Disable both buttons
		mLeftButton.setEnabled(false);
		mRightButton.setEnabled(false);
		
		// Set the pause layout as visible
		mPauseLayout.setVisibility(View.VISIBLE);
		mTimesUpLayout.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Indicate the time is up
	 */
	void timesUp() {
		// Set the condition to time up
		Global.GAME_STATE.timerCondition = TimerCondition.TIMES_UP;
		
		// Update the buttons/labels text
		this.updateButtonAndLabelText();
		
		// Disable both buttons
		mLeftButton.setEnabled(false);
		mRightButton.setEnabled(false);
		
		// Hide the delay label
		mDelayLabel.setVisibility(View.INVISIBLE);
		
		// Set the times-up layout as visible
		mPauseLayout.setVisibility(View.INVISIBLE);
		mTimesUpLayout.setVisibility(View.VISIBLE);
		
		// FIXME: once sound is in there, start screaming!
	}
	
	/**
	 * Updates the text on {@link mLeftButton}, {@link mRightButton},
	 * and {@link mDelayLabel}
	 */
	void updateButtonAndLabelText() {
		// Update button texts
		mLeftButton.setText(Global.GAME_STATE.leftPlayerTime());
		mRightButton.setText(Global.GAME_STATE.rightPlayerTime());
		
		// Update the delay label's text or visibility
		this.updateDelayLabel();
	}
	
	/**
	 * Updates the text on {@link mDelayLabel}
	 */
	private void updateDelayLabel() {
		// Update the delay label's text or visibility
		final String delayText = Global.GAME_STATE.delayTime();
		if(delayText == null) {
			// If no text is provided, set it invisible
			mDelayLabel.setVisibility(View.INVISIBLE);
		} else {
			// If text IS provided, make the label visible
			mDelayLabel.setVisibility(View.VISIBLE);
			mDelayLabel.setText(delayText);
		}
	}
}
