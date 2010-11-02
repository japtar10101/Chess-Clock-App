/**
 * 
 */
package com.app.chessclock;

import android.util.DisplayMetrics;

import com.app.chessclock.models.SettingsModel;

/**
 * Retains global variables
 * @author japtar10101
 */
public interface Global {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	/** The screen's dimensions */
	public static final DisplayMetrics DISPLAY = new DisplayMetrics();
	/** The screen's dimensions */
	public static final SettingsModel OPTIONS = new SettingsModel();
}
