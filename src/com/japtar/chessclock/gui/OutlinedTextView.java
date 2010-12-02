/**
 * 
 */
package com.japtar.chessclock.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.japtar.chessclock.R;

/**
 * @author japtar10101
 */
public class OutlinedTextView extends TextView {
	/* ===========================================================
	 * Constants
	 * =========================================================== */
	private static final float OUTLINE_PROPORTION = 0.1f;
	
	/* ===========================================================
	 * Members
	 * =========================================================== */
	private final Paint mFillPaint = new Paint();
	private final Paint mStrokePaint = new Paint();
	private int mOutlineColor = Color.TRANSPARENT;
	
	/* ===========================================================
	 * Constructors
	 * =========================================================== */
	public OutlinedTextView(Context context) {
		super(context);
		this.setupPaint();
	}
	public OutlinedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupPaint();
		this.setupAttributes(context, attrs);
	}
	public OutlinedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupPaint();
		this.setupAttributes(context, attrs);
	}
	
	/* ===========================
	    chessclock:outlineColor="#ff0================================
	 * Overrides
	 * =========================================================== */
	@Override
    protected void onDraw(Canvas canvas) {
		// Get the text to print
        final CharSequence text = super.getText();
        final float textSize = super.getTextSize();
        final float originX = (super.getLeft() + super.getRight()) / 2.0f;
        final float originY = (super.getTop() + super.getBottom()) / 2.0f;
        final Typeface typeface = super.getTypeface();

        // draw the stroke
        mStrokePaint.setColor(mOutlineColor);
        mStrokePaint.setTextSize(textSize);
        mStrokePaint.setStrokeWidth(textSize * OUTLINE_PROPORTION);
        mStrokePaint.setTypeface(typeface);
		canvas.drawText(text.toString(), originX, originY, mStrokePaint);
		
        // draw the fill
        mFillPaint.setColor(super.getTextColors().getDefaultColor());
        mFillPaint.setTextSize(textSize);
        mFillPaint.setTypeface(typeface);
		canvas.drawText(text.toString(), originX, originY, mFillPaint);
    }
	
	/* ===========================================================
	 * Getters
	 * =========================================================== */
	/**
	 * @return {@link mOutlineColor}
	 */
	public int getOutlineColor() {
		return mOutlineColor;
	}
	
	/* ===========================================================
	 * Setters
	 * =========================================================== */
	/**
	 * @param outlineColor sets {@link mOutlineColor}
	 */
	public void setOutlineColor(int outlineColor) {
		mOutlineColor = outlineColor;
		super.invalidate();
	}
	
	/* ===========================================================
	 * Private/Protected Methods
	 * =========================================================== */
	private final void setupPaint() {
		mFillPaint.setAntiAlias(true);
		mStrokePaint.setAntiAlias(true);
		mFillPaint.setStyle(Paint.Style.FILL);
		mStrokePaint.setStyle(Paint.Style.STROKE);
        mFillPaint.setTextAlign(Paint.Align.CENTER);
        mStrokePaint.setTextAlign(Paint.Align.CENTER);
	}
	private final void setupAttributes(Context context, AttributeSet attrs) {
		final TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.OutlinedTextView);
        mOutlineColor = array.getColor(
        		R.styleable.OutlinedTextView_outlineColor, 0x00000000);
        array.recycle(); 
	}
}
