/**
 * 
 */
package com.japtar.chessclock.menus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.japtar.chessclock.MainActivity;
import com.japtar.chessclock.R;

/**
 * Menu for the pause layout
 * @author japtar10101
 */
class PauseSubMenu extends SubMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	
	// == Buttons ==
	/** The resume button */
	private Button mResumeButton = null;
	/** The new game button */
	private Button mNewGameButton = null;
	
	// == Labels ==
	/** Label that appears on the pause screen */
	private TextView mPauseLabel = null;
		
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see SubMenu#SubMenu(MainActivity, TimersMenu)
	 */
	PauseSubMenu(final MainActivity activity, final TimersMenu menu) {
		super(activity, menu);
	}

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	@Override
	public boolean onClick(View v) {
		// By default, return true
		boolean toReturn = false;
		
		// Check if the button clicked is the pause button
		if(mResumeButton.equals(v)) {
			this.hideMenu();
			toReturn = true;
		} else if(mNewGameButton.equals(v)) {
			
		}
		return toReturn;
	}

	@Override
	protected void setupLayout(final View v) {
		// == Load up all the member variables ==
		
		// Grab the buttons
		mResumeButton = mParentMenu.getButton(v, R.id.buttonResume);
		mNewGameButton = mParentMenu.getButton(v, R.id.buttonNewGame);
		
		// Grab the label
		mPauseLabel = (TextView) v.findViewById(R.id.labelPause);

		// == Setup the member variables ==

		// Update the text size on everything
		mResumeButton.setTextSize(MainActivity.msTextSize * 0.5f);
		mNewGameButton.setTextSize(MainActivity.msTextSize * 0.5f);
		mPauseLabel.setTextSize(MainActivity.msTextSize);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.sub_pause;
	}
}
