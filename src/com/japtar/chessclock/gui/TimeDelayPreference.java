/**
 * <p>
 * Package of GUI widgets
 * </p>
 * 
 * <hr/>
 * 
 * <p>
 * Chess Clock App 
 * Copyright 2011 Taro Omiya
 * </p>
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
		mMinutesAdapter = new NumericWheelAdapter(context, 0, 60);
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet, int)
	 */
	public TimeDelayPreference(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		mMinutesAdapter = new NumericWheelAdapter(context, 0, 60);
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
