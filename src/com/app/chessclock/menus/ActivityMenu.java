/**
 * 
 */
package com.app.chessclock.menus;

import com.app.chessclock.MainActivity;

/**
 * TODO: add a description
 * @author japtar10101
 */
public abstract class ActivityMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The main activities class */
	protected MainActivity mParentActivity;

	/* ===========================================================
	 * Constructor
	 * =========================================================== */
	/**
	 * @param parent sets {@link mParentActivity}
	 */
	public ActivityMenu(final MainActivity parent) {
		mParentActivity = parent;
	}
	
	/* ===========================================================
	 * Abstract Methods
	 * =========================================================== */
	/**
	 * Sets up this menu
	 */
	public abstract void setupLayout();
	
	/**
	 * Cleans up this menu
	 */
	public abstract void exitLayout();
	
	/**
	 * @return true if the menu button should be enabled
	 */
	public abstract boolean enableMenuButton();
}
