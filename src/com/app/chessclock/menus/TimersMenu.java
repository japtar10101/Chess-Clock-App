/**
 * 
 */
package com.app.chessclock.menus;

import android.widget.Button;
import android.widget.TextView;

import com.app.chessclock.Global;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;
import com.app.chessclock.enums.TimerCondition;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class TimersMenu extends ActivityMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	// == Conditionals ==
	/** The current timer's condition */
	private TimerCondition mCondition = TimerCondition.STARTING;
	
	// == Buttons ==
	/** Left player's button */
	private Button mLeftbutton;
	/** Right player's button */
	private Button mRightbutton;
	
	// == Labels ==
	/** Label indicating delay */
	private TextView mDelayLabel;
	
	// == Layouts ==
	// FIXME: create a pause menu layout
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see ActivityMenu#ActivityLayout(MainActivity)
	 */
	public TimersMenu(MainActivity parent) {
		super(parent);
		
		// TODO: grab the labels and button attributes
	}

	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * TODO: add a description
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
			mCondition = TimerCondition.STARTING;
		}
	}

	/**
	 * TODO: add a description
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

	/* ===========================================================
	 * Public Methods
	 * =========================================================== */

	/* ===========================================================
	 * Getters
	 * =========================================================== */

	/* ===========================================================
	 * Setters
	 * =========================================================== */

	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	private void startup() {
		// FIXME: update the buttons, and hide all the text
	}
}
