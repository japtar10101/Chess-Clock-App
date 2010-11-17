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
 * TODO: add a description
 * @author japtar10101
 */
public class TimerPreference extends DialogPreference {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** The TimeModel to model around */
	private TimeModel mModel = null;
	/** The dialog to display */
	private final TimePicker mView;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see Preference#Preference(Context, AttributeSet)
	 */
	public TimerPreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		mView = new TimePicker(context, attrs);
		mView.setIs24HourView(true);
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet, int)
	 */
	public TimerPreference(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		mView = new TimePicker(context, attrs, defStyle);
		mView.setIs24HourView(true);
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */	
	/**
	 * Updates {@link mModel}, if user clicked "OK"
	 * @see android.preference.DialogPreference#onDialogClosed(boolean)
	 */
	@Override
	public void onDialogClosed(boolean positiveResult) {
		// Update model
		if(positiveResult) {
			mModel.setMinutes(mView.getCurrentHour().byteValue());
			mModel.setSeconds(mView.getCurrentMinute().byteValue());
		}
	}
	
	@Override
	protected View onCreateDialogView() {
		// Update view
		if(mModel != null) {
			mView.setCurrentHour((int) mModel.getMinutes());
			mView.setCurrentHour((int) mModel.getSeconds());
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
