package com.app.chessclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        
        // Recall and update the options and the last game's state.
        Global.SAVED_DATA = this.getPreferences(Context.MODE_PRIVATE);
        if(Global.SAVED_DATA != null) {
        	Global.OPTIONS.recallSettings(Global.SAVED_DATA);
        	Global.GAME_STATE.recallSettings(Global.SAVED_DATA);
        }
        
        // Also update the delay label
        Global.GAME_STATE.setDelayPrependString(
        		this.getString(R.string.delayLabelText));
        
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
        if(Global.SAVED_DATA != null) {
        	Global.GAME_STATE.saveSettings(Global.SAVED_DATA);
        }
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
				// Close out of the Main Menu
				mMainMenu.exitMenu();
				
				// Launch Preference activity
				this.startActivity(mOptionsMenu);
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
}
