/**
 * 
 */
package com.app.chessclock.gui;

import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.app.chessclock.models.TimeModel;

/**
 * GUI element that opens a timer dialog
 * @author japtar10101
 */
public class TimerPreference extends DialogPreference implements
		TimePicker.OnTimeChangedListener {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The TimeModel to model around */
	private TimeModel mModel = null;
	private int mMinutes;
	private int mSeconds;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see Preference#Preference(Context, AttributeSet)
	 */
	public TimerPreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet, int)
	 */
	public TimerPreference(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * Updates internal time variables
	 * @see android.widget.TimePicker.OnTimeChangedListener#onTimeChanged(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		mMinutes = hourOfDay;
		mSeconds = minute;
	}
	
	/**
	 * Updates {@link mModel}, if user clicked "OK"
	 * @see android.preference.DialogPreference#onDialogClosed(boolean)
	 */
	@Override
	public void onDialogClosed(boolean positiveResult) {
		// Update model
		if(positiveResult) {
			this.getKey();
			mModel.setMinutes(TimeModel.intToByte(mMinutes));
			mModel.setSeconds(TimeModel.intToByte(mSeconds));
		}
	}
	
	@Override
	protected View onCreateDialogView() {
		final TimePicker mView = new TimePicker(this.getContext());
		mView.setIs24HourView(true);

		// Update view
		if(mModel != null) {
			mMinutes = mModel.getMinutes();
			mSeconds = mModel.getSeconds();
			mView.setCurrentHour(mMinutes);
			mView.setCurrentMinute(mSeconds);
		}
		
		// Return view
		return mView;
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
	}
}
