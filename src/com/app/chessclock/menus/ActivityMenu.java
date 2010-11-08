/**
 * Package of menus
 */
package com.app.chessclock.menus;

/**
 * Interface for each menu
 * @author japtar10101
 */
public interface ActivityMenu {
	/* ===========================================================
	 * Abstract Methods
	 * =========================================================== */
	/**
	 * Sets up this menu
	 */
	public abstract void setupMenu();
	
	/**
	 * Cleans up this menu
	 */
	public abstract void exitMenu();
	
	/**
	 * @return true if the menu button should be enabled
	 */
	public abstract boolean enableMenuButton();
}
