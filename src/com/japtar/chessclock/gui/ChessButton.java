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
package com.japtar.chessclock.gui;

import com.japtar.chessclock.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Specialized buttons for this application.
 * Can display a flipped image and text
 * @author japtar10101
 */
public class ChessButton extends ImageButton {
	/* ===========================================================
	 * Members
	 * =========================================================== */
	public boolean flipHorizontally;
	private boolean mPastFlipValue = false;

	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/** @see Button#Button(Context) */
	public ChessButton(final Context context) {
		super(context);
		flipHorizontally = mPastFlipValue;
	}

	/** @see Button#Button(Context, AttributeSet) */
	public ChessButton(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.setupAttributes(context, attrs);
	}
	
	/** @see Button#Button(Context, AttributeSet, int) */
	public ChessButton(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		this.setupAttributes(context, attrs);
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	@Override
    protected void onDraw(final Canvas canvas) {
		// Check if we need to flip the button image
		if(flipHorizontally) {
			// If so, scale the canvas
			canvas.scale(-1f, 1f,
					super.getWidth() * 0.5f, super.getHeight() * 0.5f);
		}

	    // Draw the button!
	    super.onDraw(canvas);
	}

	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	private final void setupAttributes(Context context, AttributeSet attrs) {
		final TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ChessButton);
		flipHorizontally = array.getBoolean(
        		R.styleable.ChessButton_flipHorizontally, mPastFlipValue);
        array.recycle(); 
	}
}
