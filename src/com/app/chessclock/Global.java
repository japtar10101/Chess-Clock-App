/**
 * 
 */
package com.app.chessclock;

import android.os.Bundle;
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
	/** The screen's dimensions */
	public static final DisplayMetrics DISPLAY = new DisplayMetrics();
	/** The screen's dimensions */
	public static final Bundle SAVED_STATE = new Bundle();
	/** The screen's dimensions */
	public static final TimerOption OPTIONS = new TimerOption();
	/** The options menu */
	public static final ActivityLayout OPTIONS_MENU = new OptionsMenu();
	/** The timer's menu */
	public static final ActivityLayout TIMER_MENU = new TimersMenu();
}
