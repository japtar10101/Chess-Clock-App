/**
 * 
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
 * Specialized buttons for this application
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
