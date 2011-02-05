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
package com.japtar.chessclock.menus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.japtar.chessclock.MainActivity;
import com.japtar.chessclock.R;

/**
 * Menu for the pause layout
 * @author japtar10101
 */
class PauseSubMenu extends SubMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	
	// == Buttons ==
	/** The Options button */
	private Button mOptionsButton = null;
	/** The resume button */
	private Button mResumeButton = null;
	/** The new game button */
	private Button mNewGameButton = null;
	
	// == Labels ==
	/** Label that appears on the pause screen */
	private TextView mPauseLabel = null;
		
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see SubMenu#SubMenu(MainActivity, TimersMenu)
	 */
	PauseSubMenu(final MainActivity activity, final TimersMenu menu) {
		super(activity, menu);
	}

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	@Override
	protected int getLayoutId() {
		return R.layout.sub_pause;
	}
	
	@Override
	protected void setupLayout(final View v) {
		// == Load up all the member variables ==
		
		// Grab the buttons
		mOptionsButton = mParentMenu.getButton(v, R.id.buttonSettings);
		mResumeButton = mParentMenu.getButton(v, R.id.buttonResume);
		mNewGameButton = mParentMenu.getButton(v, R.id.buttonNewGame);
		
		// Grab the label
		mPauseLabel = (TextView) v.findViewById(R.id.labelPause);

		// == Setup the member variables ==

		// Update the text size on everything
		mOptionsButton.setTextSize(MainActivity.msTextSize * 0.3f);
		mResumeButton.setTextSize(MainActivity.msTextSize * 0.7f);
		mNewGameButton.setTextSize(MainActivity.msTextSize * 0.3f);
		mPauseLabel.setTextSize(MainActivity.msTextSize);
	}
	
	@Override
	public void onClick(final View v) {
		if(v != null) {
			// Check which button is clicked
			if(v.equals(mResumeButton)) {
				// Hide this menu
				this.hideMenu();
				
				// Resume the timer
				mParentMenu.resume();
			}  else if(v.equals(mOptionsButton)) {
				// Reveal the options menu
				mParentActivity.displayOptionsMenu();
			} else if(v.equals(mNewGameButton)) {
				// Hide this menu
				this.hideMenu();
				
				// Start over the timer
				mParentMenu.startup();
			}
		}
	}
}
