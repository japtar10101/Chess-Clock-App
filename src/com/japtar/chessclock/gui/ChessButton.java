/**
 * 
 */
package com.japtar.chessclock.gui;

import android.content.Context;
import android.graphics.Paint;
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
	/** Used to draw text */
	final Paint mStrokePaint = new Paint();

	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	/** @see Button#Button(Context) */
	public ChessButton(final Context context) {
		super(context);
	}

	/** @see Button#Button(Context, AttributeSet) */
	public ChessButton(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}
	
	/** @see Button#Button(Context, AttributeSet, int) */
	public ChessButton(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	/**
	 * @see android.view.View#draw(android.graphics.Canvas)
	 */
	/*
	@Override
	public void draw(final Canvas canvas) {
		// Draw the button as normal
		super.draw(canvas);
		
		// Setup the stroke paint
		final float textSize = this.getTextSize();
		mStrokePaint.setARGB(255, 255, 255, 255);
	    mStrokePaint.setTextSize(textSize);
	    mStrokePaint.setStrokeWidth(2);
	    //mStrokePaint.setTextAlign(Align.LEFT);
	    mStrokePaint.setTextAlign(Align.CENTER);
	    mStrokePaint.setTypeface(this.getTypeface());
	    mStrokePaint.setStyle(Paint.Style.STROKE);

	    // Draw the text!
	    canvas.drawText(this.getText().toString(), 100, 100, mStrokePaint);
	}
	*/

	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */

}
