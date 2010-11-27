package com.japtar.chessclock.menus;

import android.view.View;

import com.japtar.chessclock.MainActivity;

public class StartSubMenu extends SubMenu {
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/**
	 * @see SubMenu#SubMenu(MainActivity, TimersMenu)
	 */
	StartSubMenu(final MainActivity activity, final TimersMenu menu) {
		super(activity, menu);
	}

	@Override
	public boolean onClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setupLayout(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
