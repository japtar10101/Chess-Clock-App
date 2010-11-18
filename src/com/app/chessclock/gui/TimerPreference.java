/**
 * 
 */
package com.app.chessclock.gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * GUI element that opens a timer dialog
 * @author japtar10101
 */
public class TimerPreference extends DialogPreference implements
		TimePicker.OnTimeChangedListener {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	public static final String APPEND_KEY_MINUTES = "Minutes";
	public static final String APPEND_KEY_SECONDS = "Seconds";
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
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
		// If we clicked OK...
		if(positiveResult) {
			// Save the values
            this.saveValues();
		}
	}
	
	@Override
	protected View onCreateDialogView() {
		// Create a new TimePicker
		final TimePicker timer = new TimePicker(this.getContext());
		timer.setIs24HourView(true);
		
		// Recall the stored values
    	this.recallValues();
		
		// Update view
		timer.setCurrentHour(mMinutes);
		timer.setCurrentMinute(mSeconds);
		
		// Return view
		timer.setOnTimeChangedListener(this);
		return timer;
	}
	
	/**
	 * Returns a default integer
	 * @see android.preference.Preference#onGetDefaultValue(android.content.res.TypedArray, int)
	 */
	@Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // This preference type's value type is Integer, so we read the default
        // value from the attributes as an Integer.
        return a.getInteger(index, 0);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
        	// Recall the stored values
        	this.recallValues();
        } else {
            // Set state based on default value
            int value = (Integer) defaultValue;
            mMinutes = value / 60;
            mSeconds = value % 60;
        }
    }
    
    /* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
    /**
     * Recalls the stored minutes and seconds
     */
    private void recallValues() {
    	// Grab the key
		final String key = this.getKey();
		
        // Restore state
    	final SharedPreferences savedData = this.getSharedPreferences();
		mMinutes = savedData.getInt(key + APPEND_KEY_MINUTES, 0);
		mSeconds = savedData.getInt(key + APPEND_KEY_SECONDS, 0);
    }
    
    /**
     * Saves the minutes and seconds
     */
    private void saveValues() {
    	// Grab the key
		final String key = this.getKey();
		
		// Grab the editor
		final SharedPreferences toSave = this.getSharedPreferences();
		final SharedPreferences.Editor editor = toSave.edit();
		
		// Write in the preferences
		editor.putInt(key + APPEND_KEY_MINUTES, mMinutes);
		editor.putInt(key + APPEND_KEY_SECONDS, mSeconds);
		editor.commit();
    }
}
