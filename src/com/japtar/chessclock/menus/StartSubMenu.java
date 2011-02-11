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
import android.widget.LinearLayout.LayoutParams;

import com.japtar.chessclock.Global;
import com.japtar.chessclock.MainActivity;
import com.japtar.chessclock.R;

public class StartSubMenu extends SubMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	
	// == Buttons ==
	/** The right button */
	private Button mRightButton = null;
	/** The left button */
	private Button mLeftButton = null;
	/** The options button */
	private Button mSettingsButton = null;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see SubMenu#SubMenu(MainActivity, TimersMenu)
	 */
	StartSubMenu(final MainActivity activity, final TimersMenu menu) {
		super(activity, menu);
	}

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	@Override
	protected int getLayoutId() {
		return R.layout.sub_start_game;
	}

	@Override
	protected void setupLayout(final View v) {
		// == Load up all the member variables ==
		
		// Grab the buttons
		mRightButton = mParentMenu.getButton(v, R.id.buttonRightStart);
		mLeftButton = mParentMenu.getButton(v, R.id.buttonLeftStart);
		mSettingsButton = mParentMenu.getButton(v, R.id.buttonSettings);
		
		// == Setup the member variables ==

		// Update the text size on everything
		mRightButton.setTextSize(MainActivity.msTextSize * 0.65f);
		mLeftButton.setTextSize(MainActivity.msTextSize * 0.65f);
		mSettingsButton.setTextSize(MainActivity.msTextSize * 0.7f);
		
		// Update the margins
		LayoutParams params = (LayoutParams) mRightButton.getLayoutParams();
		params.rightMargin = Global.DISPLAY.widthPixels / 40;
		params.leftMargin = Global.DISPLAY.widthPixels / 40;
		params = (LayoutParams) mLeftButton.getLayoutParams();
		params.rightMargin = Global.DISPLAY.widthPixels / 40;
		params.leftMargin = Global.DISPLAY.widthPixels / 40;
	}

	@Override
	public void onClick(final View v) {
		if(v != null) {
			// Check which button is clicked
			if(v.equals(mSettingsButton)) {
				// Open the options menu
				mParentActivity.displayOptionsMenu();
			} else {
				final boolean leftPlayersTurn = v.equals(mRightButton);
				if(leftPlayersTurn || v.equals(mLeftButton)) {
					// Hide this menu
					this.hideMenu();
					
					// Update who's turn it is
					mParentMenu.updatePlayersTurn(leftPlayersTurn);
					
					// Resume the timer
					mParentMenu.resume();
				}
			}
		}
	}
}
