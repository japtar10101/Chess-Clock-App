/**
 * 
 */
package com.app.chessclock.options;

import android.app.Activity;

import com.app.chessclock.ActivityLayout;
import com.app.chessclock.R;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class OptionsMenu implements ActivityLayout {
	/* ===========================================================
	 * Constants
	 * =========================================================== */

	/* ===========================================================
	 * Members
	 * =========================================================== */

	/* ===========================================================
	 * Constructors
	 * =========================================================== */

	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * TODO: add a description
	 * @see com.app.chessclock.ActivityLayout#setupLayout(android.app.Activity)
	 */
	@Override
	public void setupLayout(final Activity main) {
		// First, setup the UI
		main.setContentView(R.layout.options);
		
		// TODO: grab the timer-related stuff, and set it to the same thing
		// as in options
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
