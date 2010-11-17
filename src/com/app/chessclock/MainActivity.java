package com.app.chessclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.chessclock.enums.TimerCondition;
import com.app.chessclock.menus.OptionsMenu;
import com.app.chessclock.menus.TimersMenu;

public class MainActivity extends Activity {
	/* ===========================================================
	 * Member Variables
	 * =========================================================== */
	/** The current layout */
	private final TimersMenu mMainMenu = new TimersMenu(this);
	private Intent mOptionsMenu;
	
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

        // Grab the Display metrics
        this.getWindowManager().getDefaultDisplay().getMetrics(Global.DISPLAY);
        
        // Recall the last game's state.
        SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
    	Global.GAME_STATE.recallSettings(settings);
        
        // Also update the delay label
        Global.GAME_STATE.setDelayPrependString(
        		this.getString(R.string.delayLabelText));
        
        // Recall and the last game's state.
        settings = PreferenceManager.getDefaultSharedPreferences(this);
    	Global.OPTIONS.recallSettings(settings);
        
        // Create the options menu
        mOptionsMenu = new Intent(MainActivity.this, OptionsMenu.class);
        
        // Start the main menu
        mMainMenu.setupMenu();
    }
        
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {    	
    	// Do whatever is in the super class first
        super.onPause();
        
        // Exit the current layout
        mMainMenu.exitMenu();
    }
    
    /**
     * Called when the activity pauses.
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onStop() {
    	// Do whatever is in the super class first
        super.onStop();

        // Save pause state
        SharedPreferences settings = this.getPreferences(MODE_PRIVATE);
    	Global.GAME_STATE.saveSettings(settings);
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
	 * Switches the layout based on the item selected
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		boolean toReturn = true;
			
		// Handle item selection
		switch(item.getItemId()) {
			// If options is clicked, go to options menu
			case R.id.menuOptions:
				this.openOptionsMenu();
				break;
			    
			// If reset is clicked, restart the timers menu
			case R.id.menuReset:
				Global.GAME_STATE.timerCondition = TimerCondition.STARTING;
				mMainMenu.setupMenu();
				break;
			
			// Otherwise, let the super class figure it out
			default:
				toReturn = super.onOptionsItemSelected(item);
				break;
		}
		return toReturn;
	}
	
	/* ===========================================================
	 * Public method
	 * =========================================================== */
	/**
	 * Switches to the options menu
	 */
	public void displayOptionsMenu() {
		// Close out of the Main Menu
		mMainMenu.exitMenu();
		
		// Launch Preference activity
		this.startActivity(mOptionsMenu);
	}
}
