/**
 * 
 */
package com.app.chessclock;

import android.util.DisplayMetrics;

import com.app.chessclock.models.GameStateModel;
import com.app.chessclock.models.OptionsModel;

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
	/** The app's options */
	public static final OptionsModel OPTIONS = new OptionsModel();
	/** The game's state */
	public static final GameStateModel GAME_STATE = new GameStateModel();
}
