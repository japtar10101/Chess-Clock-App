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

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import com.japtar.chessclock.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.japtar.chessclock.models.TimeModel;

/**
 * GUI element that opens a timer dialog
 * @author japtar10101
 */
public abstract class BaseTimePreference extends DialogPreference {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	/** This preference's time */
	protected final TimeModel mTime = new TimeModel();
	/** The GUI for this dialog preference */
	private ViewGroup mTimeWheel = null;
	/** The GUI indicating the minutes */
	protected WheelView mMinutes = null;
	/** The GUI indicating the seconds */
	protected WheelView mSeconds = null;
	private final NumericWheelAdapter mSecondsAdapter;
	/** The default value */
	private int mDefaultValue = 0;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see Preference#Preference(Context, AttributeSet)
	 */
	public BaseTimePreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		mSecondsAdapter = new NumericWheelAdapter(context, 0, 59, "%02d");
	}
	
	/**
	 * @see Preference#Preference(Context, AttributeSet, int)
	 */
	public BaseTimePreference(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		mSecondsAdapter = new NumericWheelAdapter(context, 0, 59, "%02d");
	}
	
	/* ===========================================================
	 * Abstract methods
	 * =========================================================== */
	
	protected abstract NumericWheelAdapter getMinutesAdapter(final Context context);
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	
	/**
	 * Updates {@link mModel}, if user clicked "OK"
	 * @see android.preference.DialogPreference#onDialogClosed(boolean)
	 */
	@Override
	public void onDialogClosed(boolean positiveResult) {
		// If we clicked OK...
		if(positiveResult) {
			// Grab the values
			mTime.setMinutes(mMinutes.getCurrentItem());
			mTime.setSeconds(mSeconds.getCurrentItem());
			
			// Revert the GUI
			mTimeWheel = null;
			mMinutes = null;
			mSeconds = null;
            
			// Save the values
            this.saveValues();
		}
	}
	
	@Override
	protected View onCreateDialogView() {
		// Setup variables
		final Context context = this.getContext();
		
		// Setup the wheel UI
		mTimeWheel = (ViewGroup) ViewGroup.inflate(
				context, R.xml.timer_preference, null);
		mMinutes = (WheelView) mTimeWheel.findViewById(R.id.minutes);
		mSeconds = (WheelView) mTimeWheel.findViewById(R.id.seconds);
		final int width = (int) (mTimeWheel.getWidth() / 20d);
		
		// Configure the minutes wheel
		mMinutes.setViewAdapter(this.getMinutesAdapter(context));
		mMinutes.setLabel("Mins");
		mMinutes.setLabelWidth(width);
		
		// Configure the seconds wheel
		mSeconds.setViewAdapter(mSecondsAdapter);
		mSeconds.setLabel("Secs");
		mSeconds.setLabelWidth(width);
		
		// Setup the window to the set time
		mMinutes.setCurrentItem(mTime.getMinutes());
		mSeconds.setCurrentItem(mTime.getSeconds());
		return mTimeWheel;
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

    /**
     * Sets the initial value.
     * @see android.preference.Preference#onSetInitialValue(boolean, java.lang.Object)
     */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        // Check if we should recall the values instead
        if(restoreValue && this.shouldPersist()) {
        	// If so, recall or save the values
        	this.recallValues();
        } else if(defaultValue instanceof Integer) {
        	// Set state based on default value
        	mDefaultValue = (Integer) defaultValue;
        	mTime.setMinutes(mDefaultValue / 60);
        	mTime.setSeconds(mDefaultValue % 60);
    	}
    }
    
    /* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
    /**
     * Recalls the stored minutes and seconds
     */
    private void recallValues() {
        // Restore state
    	mTime.recallTime(this.getSharedPreferences(),
    			this.getKey(), mDefaultValue);
    }
    
    /**
     * Saves the minutes and seconds
     */
    private void saveValues() {
		// Grab the editor
		final SharedPreferences toSave = this.getSharedPreferences();
		final SharedPreferences.Editor editor = toSave.edit();
		
		// Write in the preferences
		mTime.saveTime(editor, this.getKey());
		editor.commit();
    }
}
