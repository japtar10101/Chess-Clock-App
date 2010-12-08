/**
 * Package of enumerators
 */
package com.japtar.chessclock.enums;

/**
 * All the possible delay modes
 * @author japtar10101
 */
public interface DelayMode {
	/** A delay is made before the start of each player's turn.
	 * This value is static, and cannot be accumulated. */
	public static final byte BASIC = 0;
	/** A delay time is added <i>before</i> a player's turn.
	 * The delay duration can be accumulated. If the player
	 * moves faster than the given delay duration, the remaining time
	 * is retained and added next turn. */
	public static final byte FISCHER = 1;
	/** A delay time is added <i>after</i> a player's turn.
	 * The delay duration can be accumulated. If the player
	 * moves faster than the given delay duration, the remaining time
	 * is retained and added next turn. */
	public static final byte FISCHER_AFTER = 2;
	/** A delay time is added <i>after</i> a player's turn.
	 * The delay duration can be accumulated. If the player
	 * moves faster than the given delay duration, the remaining time
	 * is retained and added next turn. */
	public static final byte BRONSTEIN = 3;
	/** Indicates when the time is up */
	public static final byte HOUR_GLASS = 4;
	/** Number of Modes */
	public static final byte NUM_MODES = 5;
}
