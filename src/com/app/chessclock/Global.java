/**
 * 
 */
package com.app.chessclock;

import android.util.DisplayMetrics;

import com.app.chessclock.models.Options;

/**
 * Retains global variables
 * @author japtar10101
 */
public interface Global {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	
	// Device-related attributes
	/** The screen's dimensions */
	public static final DisplayMetrics DISPLAY = new DisplayMetrics();
	/** The screen's dimensions */
	public static final Options OPTIONS = new Options();
}
