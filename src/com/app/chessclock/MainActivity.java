package com.app.chessclock;

import java.security.InvalidParameterException;
import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.chessclock.enums.TimerCondition;
import com.app.chessclock.menus.OptionsMenu;
import com.app.chessclock.menus.TimersMenu;
import com.app.chessclock.models.GameStateModel;

public class MainActivity extends Activity {
	/* ===========================================================
	 * Member Variables
	 * =========================================================== */
	/** The current layout */
	private final TimersMenu mMainMenu = new TimersMenu(this);
	private final TimersMenu mOptionsMenu = new OptionsMenu(this);
	
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
        
        // Recall and update the last game state.
        SharedPreferences settings = this.getSharedPreferences(
        		GameStateModel.PREFERENCE_FILE_NAME, 0);
        Global.GAME_STATE.recallSettings(settings);
        
        // Also update the delay label
        Global.GAME_STATE.setDelayPrependString(
        		this.getString(R.string.delayLabelText));
        
        // TODO: once the options menu is done, recall the preferences from there
//        SharedPreferences settings = this.getSharedPreferences(
//        		GameStateModel.PREFERENCE_FILE_NAME, 0);
//        Global.OPTIONS.setSavedState(savedInstanceState);
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
        final SharedPreferences settings = this.getSharedPreferences(
        		GameStateModel.PREFERENCE_FILE_NAME, 0);
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
        
        // TODO: When the options menu is fixed, take this out
        final MenuItem settings = (MenuItem) menu.getItem(1);
        settings.setEnabled(false);
        
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
			// TODO: If options is clicked, go to options menu
			case R.id.menuOptions:
				mMainMenu.exitMenu();
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
