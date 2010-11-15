/**
 * 
 */
package com.app.chessclock.gui;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.AttributeSet;
import android.widget.TimePicker;

import com.app.chessclock.models.TimeModel;

/**
 * TODO: add a description
 * @author japtar10101
 */
public class TimerPreference extends Preference implements
		OnTimeSetListener, OnPreferenceClickListener {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The TimeModel to model around */
	private TimeModel mModel = null;
	/** The dialog to display */
	private final TimePickerDialog mDialog;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see Preference#Preference(Context)
	 */
	public TimerPreference(final Context context) {
		super(context);
		mDialog = new TimePickerDialog(context, this, 0, 0, true);
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet)
	 */
	public TimerPreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		mDialog = new TimePickerDialog(context, this, 0, 0, true);
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet, int)
	 */
	public TimerPreference(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		mDialog = new TimePickerDialog(context, defStyle, this, 0, 0, true);
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */	
	/**
	 * Updates {@link mModel}
	 * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeSet(final TimePicker view,
			final int hourOfDay, final int minute) {
		if(mModel != null) {
			mModel.setMinutes(TimeModel.intToByte(hourOfDay));
			mModel.setSeconds(TimeModel.intToByte(minute));
		}
	}
	
	/**
	 * Shows a TimePickerDialog
	 * @see android.preference.Preference.OnPreferenceClickListener#onPreferenceClick(android.preference.Preference)
	 */
	@Override
	public boolean onPreferenceClick(final Preference preference) {
		mDialog.show();
		return true;
	}
	
	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param model sets {@link mModel}
	 */
	public void setModel(final TimeModel model) {
		// Update model
		mModel = model;
		
		// Update dialog
		if(mModel != null) {
			mDialog.updateTime((int) mModel.getMinutes(),
					(int) mModel.getSeconds());
		}
	}
}
