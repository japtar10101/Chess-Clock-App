/**
 * 
 */
package com.app.chessclock.timer;

import android.app.Activity;

import com.app.chessclock.ActivityLayout;
import com.app.chessclock.MainActivity;
import com.app.chessclock.R;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class TimersMenu extends ActivityLayout {
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
	public TimersMenu(MainActivity parent) {
		super(parent);
		// TODO Auto-generated constructor stub
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
		mParentActivity.setContentView(R.layout.main);
		
		// TODO: grab the labels and button attributes
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
