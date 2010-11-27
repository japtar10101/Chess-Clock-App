package com.japtar.chessclock.menus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.japtar.chessclock.MainActivity;
import com.japtar.chessclock.R;

public class TimesUpSubMenu extends SubMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	
	// == Buttons ==
	/** The new game button */
	private Button mNewGameButton = null;
	
	// == Labels ==
	/** Label that appears on the pause screen */
	private TextView mTimesUpLabel = null;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see SubMenu#SubMenu(MainActivity, TimersMenu)
	 */
	TimesUpSubMenu(final MainActivity activity, final TimersMenu menu) {
		super(activity, menu);
	}

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	@Override
	protected int getLayoutId() {
		return R.layout.sub_time_up;
	}
	
	@Override
	protected void setupLayout(final View v) {
		// == Load up all the member variables ==
		
		// Grab the buttons
		mNewGameButton = mParentMenu.getButton(v, R.id.buttonNewGame);
		
		// Grab the label
		mTimesUpLabel = (TextView) v.findViewById(R.id.labelTimesUp);
		
		// == Setup the member variables ==

		// Update the text size on everything
		mTimesUpLabel.setTextSize(MainActivity.msTextSize);
		mNewGameButton.setTextSize(MainActivity.msTextSize * 0.5f);
	}

	@Override
	public void onClick(final View v) {
		if(v != null && v.equals(mNewGameButton)) {
			// Hide this menu
			this.hideMenu();
			
			// Start over the timer
			mParentMenu.startup();
		}
	}
}
