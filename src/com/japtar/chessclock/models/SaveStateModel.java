/**
 * <p>
 * Helper classes
 * </p>
 * 
 * <hr/>
 * 
 * <p>
 * Chess Clock App 
 * Copyright 2011 Taro Omiya
 * </p>
 */
package com.japtar.chessclock.models;

import android.content.SharedPreferences;

/**
 * Interface for models dealing with storing and recalling settings
 * @author japtar10101
 */
public interface SaveStateModel {
	/**
	 * Used to save settings
	 * @param savedState the SharedPreferences to save
	 */
	public void saveSettings(final SharedPreferences saveState);
	/**
	 * Used to recall settings
	 * @param savedState the SharedPreferences to recall from
	 */
	public void recallSettings(final SharedPreferences savedState);
}
