/**
 * 
 */
package com.app.chessclock;

import android.util.DisplayMetrics;

import com.app.chessclock.options.OptionsMenu;
import com.app.chessclock.options.TimerOption;
import com.app.chessclock.timer.TimersMenu;

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
	public static final TimerOption OPTIONS = new TimerOption();
}
