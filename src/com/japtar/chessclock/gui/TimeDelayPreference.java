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
package com.japtar.chessclock.gui;

import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

/**
 * GUI element that opens a timer dialog
 * @author japtar10101
 */
public class TimeDelayPreference extends BaseTimePreference {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	private final NumericWheelAdapter mMinutesAdapter;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see Preference#Preference(Context, AttributeSet)
	 */
	public TimeDelayPreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		mMinutesAdapter = new NumericWheelAdapter(context, 0, 30);
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet, int)
	 */
	public TimeDelayPreference(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		mMinutesAdapter = new NumericWheelAdapter(context, 0, 30);
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * @see com.japtar.chessclock.gui.BaseTimePreference#getMinutesAdapter(android.content.Context)
	 */
	@Override
	protected NumericWheelAdapter getMinutesAdapter(Context context) {
		return mMinutesAdapter;
	}
}
