/**
 * 
 */
package com.app.chessclock.menus;

import com.app.chessclock.MainActivity;
import com.app.chessclock.R;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class OptionsMenu extends ActivityMenu {
	/* ===========================================================
	 * Constants
	 * =========================================================== */

	/* ===========================================================
	 * Members
	 * =========================================================== */

	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see ActivityMenu#ActivityLayout(MainActivity)
	 */
	public OptionsMenu(final MainActivity parent) {
		super(parent);
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
		mParentActivity.setContentView(R.layout.options);
		
		// TODO: grab the timer-related stuff, and set it to the same thing
		// as in options
	}

	/**
	 * TODO: add a description
	 * @see com.app.chessclock.menus.ActivityMenu#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitLayout() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return false, always
	 * @see com.app.chessclock.menus.ActivityMenu#enableMenuButton()
	 */
	@Override
	public boolean enableMenuButton() {
		return false;
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

}
