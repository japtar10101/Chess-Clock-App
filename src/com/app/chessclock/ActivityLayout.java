/**
 * 
 */
package com.app.chessclock;

/**
 * TODO: add a description
 * @author japtar10101
 */
public abstract class ActivityLayout {
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
	public ActivityLayout(final MainActivity parent) {
		mParentActivity = parent;
	}
	/**
	 * Sets up the activity to match with this layout's GUI
	 * @param main
	 */
	public abstract void setupLayout();
	/**
	 * TODO: add a description
	 * @param main
	 */
	public abstract void exitLayout();
}
