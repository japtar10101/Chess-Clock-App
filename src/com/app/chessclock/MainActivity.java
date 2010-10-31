package com.app.chessclock;

import java.security.InvalidParameterException;
import java.util.HashMap;

import com.app.chessclock.options.OptionsMenu;
import com.app.chessclock.timer.TimersMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {
	/* ===========================================================
	 * Member Variables
	 * =========================================================== */
	/** The current layout */
	private LayoutId mCurrentLayoutId = null;
	private final HashMap<LayoutId, ActivityLayout> mAllLayouts =
		new HashMap<LayoutId, ActivityLayout>();
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
    /**
     * Called when the activity is first created.
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
    	// Do whatever is in the super class first
        super.onCreate(savedInstanceState);
        
        // Update all constant variables
        this.getWindowManager().getDefaultDisplay().getMetrics(Global.DISPLAY);
        Global.OPTIONS.setSavedState(savedInstanceState);
        
        // Create all the layouts
        mAllLayouts.put(LayoutId.TIMER, new TimersMenu(this));
        mAllLayouts.put(LayoutId.OPTIONS, new OptionsMenu(this));
        
        // Set the default layout to main
        this.setCurrentLayoutId(LayoutId.TIMER);
    }
    
    /**
     * Creates the initial menu layout
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
    	// Generate the menu
        final MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        // Return true
        return true;
    }
    
    /**
     * Returns true only if the layout is currently set to timer
     * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
    	boolean toReturn = false;
    	
    	// Check if the layout is currently set to Timer
    	if(mCurrentLayoutId == LayoutId.TIMER) {
    		toReturn = true;
    	}
    	
    	return toReturn;
    }

	/**
	 * TODO: add a description
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		boolean toReturn = true;
			
		// Handle item selection
		switch(item.getItemId()) {
			// If options is clicked, go to options menu
			case R.id.menuOptions:
				this.setCurrentLayoutId(LayoutId.OPTIONS);
				break;
			    
			// If reset is clicked, restart the timers menu
			case R.id.menuReset:
				this.setCurrentLayoutId(LayoutId.TIMER);
				break;
			    
			// If quit is clicked, quit this application
			case R.id.menuQuit:
				break;
			
			// Otherwise, let the super class figure it out
			default:
				toReturn = super.onOptionsItemSelected(item);
		}
		return toReturn;
	}
    
	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Gets a layout based on ID
	 * @param layout Layout to get
	 * @throws InvalidParameterException if <b>layout</b> is null
	 */
	public ActivityLayout getLayout(final LayoutId layout) {
		ActivityLayout toReturn = null;
		
		if(mAllLayouts.containsKey(layout)) {
			toReturn = mAllLayouts.get(layout);
		}
		
		return toReturn;
	}
	
	/**
	 * Switches this activity's layout
	 * @param layout Layout to switch to
	 * @throws InvalidParameterException if <b>layout</b> is null
	 */
	public ActivityLayout getCurrentLayout() {
		return this.getLayout(mCurrentLayoutId);
	}

	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mCurrentLayoutId}
	 */
	public LayoutId getCurrentLayoutId() {
		return mCurrentLayoutId;
	}

	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param currentLayoutId sets {@link mCurrentLayoutId}
	 * @throws InvalidParameterException if <b>currentLayoutId</b> is null
	 */
	public void setCurrentLayoutId(final LayoutId currentLayoutId) {
		// Check parameter if it's null
		if(currentLayoutId == null) {
			throw new InvalidParameterException("Layout ID cannot be null");
		}
		
		// Exit the current layout
		ActivityLayout tempLayout = this.getCurrentLayout();
		tempLayout.exitLayout();
		
		// Update the layout ID
		mCurrentLayoutId = currentLayoutId;
		
		// Setup the new layout
		tempLayout = this.getCurrentLayout();
		tempLayout.setupLayout();
	}
}
