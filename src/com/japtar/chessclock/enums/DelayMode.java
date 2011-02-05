/**
 * Chess Clock App
 * https://github.com/japtar10101/Chess-Clock-App
 * 
 * The MIT License
 * 
 * Copyright (c) 2011 Taro Omiya
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
	/** One player loses time, while the other gains */
	public static final byte HOUR_GLASS = 4;
	/** Number of Modes */
	public static final byte NUM_MODES = 5;
	
	/** String version of {@link #BASIC} */
	public static final String STRING_BASIC = "basicDelay";
	/** String version of {@link #FISCHER} */
	public static final String STRING_FISCHER = "fischer";
	/** String version of {@link #FISCHER_AFTER} */
	public static final String STRING_FISCHER_AFTER = "fischerAfter";
	/** String version of {@link #BRONSTEIN} */
	public static final String STRING_BRONSTEIN = "bronstein";
	/** String version of {@link #HOUR_GLASS} */
	public static final String STRING_HOUR_GLASS = "hourGlass";
	
	/** Hash set to convert bytes to strings */
	public static final String[] BYTE_TO_STRING = new String[] {
		STRING_BASIC,
		STRING_FISCHER,
		STRING_FISCHER_AFTER,
		STRING_BRONSTEIN,
		STRING_HOUR_GLASS
	};
}
