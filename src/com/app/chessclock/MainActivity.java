package com.app.chessclock;

import java.security.InvalidParameterException;
import java.util.HashMap;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.chessclock.enums.MenuId;
import com.app.chessclock.enums.TimerCondition;
import com.app.chessclock.menus.ActivityMenu;
import com.app.chessclock.menus.TimersMenu;
import com.app.chessclock.models.GameStateModel;

public class MainActivity extends Activity {
	/* ===========================================================
	 * Member Variables
	 * =========================================================== */
	/** The current layout */
	private MenuId mCurrentMenuId = MenuId.TIMER;
	private final HashMap<MenuId, ActivityMenu> mAllMenus =
		new HashMap<MenuId, ActivityMenu>();
	
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
        
        // Create all the layouts
        mAllMenus.put(MenuId.TIMER, new TimersMenu(this));
        //mAllMenus.put(MenuId.OPTIONS, new OptionsMenu(this));
        
        // Set the default layout to main
        this.setCurrentMenuId(MenuId.TIMER);
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
        this.getCurrentMenu().exitMenu();
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
     * Returns true only if the layout is currently set to timer
     * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
    	return this.getCurrentMenu().enableMenuButton();
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
				this.setCurrentMenuId(MenuId.OPTIONS);
				break;
			    
			// If reset is clicked, restart the timers menu
			case R.id.menuReset:
				Global.GAME_STATE.timerCondition = TimerCondition.STARTING;
				this.setCurrentMenuId(MenuId.TIMER);
				break;
			
			// Otherwise, let the super class figure it out
			default:
				toReturn = super.onOptionsItemSelected(item);
				break;
		}
		return toReturn;
	}
    
	/**
	 * TODO: once the options menu is finished, delete this.
	 * If backing from the options menu, saves the options,
	 * and go directly to the game menu.
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// Check which menu we're on
		if(mCurrentMenuId == MenuId.OPTIONS) {
			// Go right back to the timer menu
			this.setCurrentMenuId(MenuId.TIMER);
		} else {
			// Otherwise, let the super class figure it out
			super.onBackPressed();
		}
	}
	
	/* ===========================================================
	 * Public Methods
	 * =========================================================== */
	/**
	 * Gets a menu based on ID
	 * @param menuId Menu to get
	 * @throws InvalidParameterException if <b>menuId</b> is null
	 */
	public ActivityMenu getMenu(final MenuId menuId) {
		ActivityMenu toReturn = null;
		
		// Make sure the ID is contained in the HashMap
		if(mAllMenus.containsKey(menuId)) {
			
			// Return the corresponding menu
			toReturn = mAllMenus.get(menuId);
		}
		
		return toReturn;
	}
	
	/**
	 * Switches this activity's layout
	 * @param layout Layout to switch to
	 */
	public ActivityMenu getCurrentMenu() {
		return this.getMenu(mCurrentMenuId);
	}

	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mCurrentMenuId}
	 */
	public MenuId getCurrentMenuId() {
		return mCurrentMenuId;
	}

	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param menuId sets {@link mCurrentMenuId}
	 * @throws InvalidParameterException if <b>currentLayoutId</b> is null
	 */
	public void setCurrentMenuId(final MenuId menuId) throws InvalidParameterException {
		// Check parameter if it's null
		if(menuId == null) {
			throw new InvalidParameterException("Menu ID cannot be null");
		}
		
		// Exit the current layout
		this.getCurrentMenu().exitMenu();
		
		// Update the layout ID
		mCurrentMenuId = menuId;
		
		// Setup the new layout
		this.getCurrentMenu().setupMenu();
	}
}
