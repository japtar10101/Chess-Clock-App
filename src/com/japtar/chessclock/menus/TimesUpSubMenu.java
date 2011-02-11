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

public class TimesUpSubMenu extends SubMenu {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	
	// == Buttons ==
	/** The new game button */
	private Button mNewGameButton = null;
	
	// == Labels ==
	/** Label that appears on the pause screen */
	private TextView mTimesUpLabel = null;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see SubMenu#SubMenu(MainActivity, TimersMenu)
	 */
	TimesUpSubMenu(final MainActivity activity, final TimersMenu menu) {
		super(activity, menu);
	}

	/* ===========================================================
	 * Override Methods
	 * =========================================================== */
	@Override
	protected int getLayoutId() {
		return R.layout.sub_time_up;
	}
	
	@Override
	protected void setupLayout(final View v) {
		// == Load up all the member variables ==
		
		// Grab the buttons
		mNewGameButton = mParentMenu.getButton(v, R.id.buttonNewGame);
		
		// Grab the label
		mTimesUpLabel = (TextView) v.findViewById(R.id.labelTimesUp);
		
		// == Setup the member variables ==

		// Update the text size on everything
		mTimesUpLabel.setTextSize(MainActivity.msTextSize);
		mNewGameButton.setTextSize(MainActivity.msTextSize * 0.7f);
	}

	@Override
	public void onClick(final View v) {
		if(v != null && v.equals(mNewGameButton)) {
			// Hide this menu
			this.hideMenu();
			
			// Start over the timer
			mParentMenu.startup();
		}
	}
}
