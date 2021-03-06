/**
 * Chess Clock App
 * https://github.com/japtar10101/Chess-Clock-App
 * 
 * The MIT License
 * 
 * Copyright (c) 2011 Taro Omiya
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.japtar.chessclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.japtar.chessclock.R;
import com.japtar.chessclock.menus.OptionsMenu;
import com.japtar.chessclock.menus.TimersMenu;

public class MainActivity extends Activity {
	/* ===========================================================
	 * Static Variables
	 * =========================================================== */
	/** The most optimal text size */
	public static int msTextSize = 10;
	
	/* ===========================================================
	 * Member Variables
	 * =========================================================== */
	/** The current layout */
	private final TimersMenu mMainMenu = new TimersMenu(this);
	/** The preference page */
	private Intent mOptionsMenu = null;
	/** The sound effects */
	private SoundPool mSoundPlayer = null;
	/** The sound effect ID */
	private int mSoundID;
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
    /**
     * Called when the activity is first created.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	// Do whatever is in the super class first
        super.onCreate(savedInstanceState);
        
        // Take care of this applications system settings
        PreferenceManager.setDefaultValues(this, R.layout.options, false);
        this.setVolumeControlStream(AudioManager.STREAM_ALARM);
        this.getWindowManager().getDefaultDisplay().getMetrics(Global.DISPLAY);
        
        // Determine the best text size
        this.calculateTextSize();
       
        // Recall all the stored settings
        this.recallAllSettings();
        
        // Create the options menu
        mOptionsMenu = new Intent(this, OptionsMenu.class);
        
        // Create sound pool
        this.setupSoundEffects();
    }

	/**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {    	
    	// Do whatever is in the super class first
        super.onPause();
        
        // Exit the current layout
        mMainMenu.exitMenu();
        
        // Destroy the sound player
        if(mSoundPlayer != null) {
	        mSoundPlayer.release();
	        mSoundPlayer = null;
        }
    }
    
    /**
     * Called when the activity resumes.
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {    	
    	// Do whatever is in the super class first
        super.onResume();
        
        // Exit the current layout
        mMainMenu.setupMenu();

        // Create sound pool
        this.setupSoundEffects();
    }
    
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
    	// Do whatever is in the super class first
        super.onDestroy();

        // Save pause state
        SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
    	Global.GAME_STATE.saveSettings(settings);
    }
	
	/* ===========================================================
	 * Public method
	 * =========================================================== */
	/**
	 * Switches to the options menu
	 */
	public void displayOptionsMenu() {
		// Close out of the Main Menu
		mMainMenu.exitMenu();
		
		// Launch Preference activity
		this.startActivity(mOptionsMenu);
	}
	
	public void playSound() {
		if(mSoundPlayer != null) {
			// Stop the previously playing sound
			mSoundPlayer.stop(mSoundID);
						
			// Play the sound
			mSoundPlayer.play(mSoundID, 1f, 1f,
					1, 0, 1f);
		}
	}
	
	/* ===========================================================
	 * Private/Protected method
	 * =========================================================== */
	/**
	 * Calculates the best text size, and sets the {@link Global#msTextSize}
	 */
	private void calculateTextSize() {
		// Find the maximum screen width (plus a bit of padding)
		final int maxWidth;
		if(Global.DISPLAY.widthPixels > Global.DISPLAY.heightPixels) {
			maxWidth = Global.DISPLAY.widthPixels / 2 -
				Global.DISPLAY.widthPixels / 20;
		} else {
			maxWidth = Global.DISPLAY.heightPixels / 2 -
				Global.DISPLAY.heightPixels / 20;
		}
		
		// Create a temporary text view, with half the screen width
		final TextView testTextWidth = new TextView(this);
		testTextWidth.setHeight(Global.DISPLAY.heightPixels);
		testTextWidth.setWidth(maxWidth);
		testTextWidth.setTextSize(msTextSize);
		testTextWidth.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		this.setContentView(testTextWidth);
		
		// Calculate the text size
	    while(testTextWidth.getPaint().measureText("000:00") < maxWidth) {
	    	testTextWidth.setTextSize(++msTextSize);
	    }
	    --msTextSize;
	    testTextWidth.destroyDrawingCache();
	}
	
	/**
	 * Recalls the stored game settings
	 */
	private void recallAllSettings() {
		// Recall the last game's state.
        SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
    	Global.GAME_STATE.recallSettings(settings);
        
        // Also update the delay label
        Global.GAME_STATE.setDelayPrependString(
        		this.getString(R.string.delayLabelText));
        
        // Recall the options.
        settings = PreferenceManager.getDefaultSharedPreferences(this);
    	Global.OPTIONS.recallSettings(settings);
	}

	/**
	 * Sets up the sound effects
	 */
	private void setupSoundEffects() {
		if(mSoundPlayer == null) {
		    mSoundPlayer = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
		    mSoundID = mSoundPlayer.load(this, R.raw.snap, 1);
		}
	}
}
