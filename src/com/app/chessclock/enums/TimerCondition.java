/**
 * 
 */
package com.app.chessclock.enums;

/**
 * TODO: add a description
 * @author japtar10101
 */
public enum TimerCondition {
	/** Indicates the timer just started */
	STARTING,
	/** Indicates the timer is running */
	RUNNING,
	/** Indicates the timer is paused, due to opening the menu */
	PAUSE_WITH_MENU,
	/** Indicates the timer is paused, without the menu */
	PAUSE_WITHOUT_MENU,
	/** Indicates when the time is up */
	TIMES_UP
}
