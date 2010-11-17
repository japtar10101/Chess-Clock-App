/**
 * Package of menus
 */
package com.app.chessclock.menus;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.Vibrator;
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
public class TimersMenu implements OnClickListener, ActivityMenu {
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
	/** Task that rings the alarm */
	private final Runnable mStopAlarm;
	
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
	
	// == Misc. ==
	/** The vibrator */
	private Vibrator mSmallVibrate = null;
	/** Sound of alarm */
	private Ringtone mRingtone = null;
	
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
		mStopAlarm = new Runnable() {
			public void run() {
				if(mRingtone != null && mRingtone.isPlaying()) {
					mRingtone.stop();
				}
			}
		};
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
		
		// Get the vibrator and ringtone
		mSmallVibrate = (Vibrator) mParentActivity.getSystemService(
				Context.VIBRATOR_SERVICE);
		mRingtone = RingtoneManager.getRingtone(mParentActivity,
				Global.OPTIONS.alarmUri);
		
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
		// Stop the time handler
		mTimer.removeCallbacks(mTask);

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
		// Vibrate in response to a button press
		if(mSmallVibrate != null) {
			mSmallVibrate.vibrate(50);
		}
		
		// Check if the button clicked is the new game button
		if(v.equals(mPauseButton)) {
			switch(Global.GAME_STATE.timerCondition) {
				// If running, pause the game
				case TimerCondition.RUNNING:
					this.paused();
					return;
				// go to options screen 
				case TimerCondition.STARTING:
					mParentActivity.displayOptionsMenu();
					return;
				// If time's up, restart the game
				case TimerCondition.TIMES_UP:
					this.startup();
					return;
				case TimerCondition.PAUSE:
				default:
					mPauseButton.setText(mParentActivity.getString(
							R.string.pauseButtonText));
			}
		}
		
		// Stop the handler
		mTimer.removeCallbacks(mTask);
		
		// If just starting, update the labels
		if(Global.GAME_STATE.timerCondition == TimerCondition.STARTING) {
			// Update to the time text
			this.updateButtonAndLabelText();
		}
		
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
			
			// Update the pause button text
			mPauseButton.setText(mParentActivity.getString(R.string.pauseButtonText));
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
		
		// Enable both buttons
		mLeftButton.setEnabled(true);
		mRightButton.setEnabled(true);
		
		// Update button's text
		mLeftButton.setText(mParentActivity.getString(R.string.playerButtonText));
		mRightButton.setText(mParentActivity.getString(R.string.playerButtonText));
		mPauseButton.setText(mParentActivity.getString(R.string.settingsMenuLabel));
		mDelayLabel.setVisibility(View.INVISIBLE);
		
		// Set both layouts to be invisible
		mPauseLayout.setVisibility(View.INVISIBLE);
		mTimesUpLayout.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Indicate the game is paused
	 */
	private void paused() {
		// Stop the handler
		mTimer.removeCallbacks(mTask);

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
		
		// Update the pause button text
		mPauseButton.setText(mParentActivity.getString(R.string.resumeButtonText));
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
		
		// Start screaming!
		if(mRingtone != null) {
			mRingtone.play();
			mTimer.postDelayed(mStopAlarm, 3000);
		}
		
		// Update the pause button text
		mPauseButton.setText(mParentActivity.getString(R.string.newGameButtonText));
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
