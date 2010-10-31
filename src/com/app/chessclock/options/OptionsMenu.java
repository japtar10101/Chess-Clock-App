/**
 * 
 */
package com.app.chessclock.options;

import com.app.chessclock.ActivityLayout;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class OptionsMenu extends ActivityLayout {
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
	 * @see ActivityLayout#ActivityLayout(MainActivity)
	 */
	public OptionsMenu(final MainActivity parent) {
		super(parent);
	}

	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * TODO: add a description
	 * @see com.app.chessclock.ActivityLayout#setupLayout(android.app.Activity)
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
	 * @see com.app.chessclock.ActivityLayout#exitLayout(android.app.Activity)
	 */
	@Override
	public void exitLayout() {
		// TODO Auto-generated method stub
		
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
