/**
 * <p>
 * Package of enumerators
 * </p>
 * 
 * <hr/>
 * 
 * <p>
 * Chess Clock App 
 * Copyright 2011 Taro Omiya
 * </p>
 */
package com.japtar.chessclock.enums;

/**
 * All the possible game state conditions
 * @author japtar10101
 */
public interface TimerCondition {
	/** Indicates the timer just started */
	public static final byte STARTING = 0;
	/** Indicates the timer is running */
	public static final byte RUNNING = 1;
	/** Indicates the timer is paused */
	public static final byte PAUSE = 2;
	/** Indicates when the time is up */
	public static final byte TIMES_UP = 3;
	/** Number of TimerConditions */
	public static final byte NUM_CONDITIONS = 4;
}
