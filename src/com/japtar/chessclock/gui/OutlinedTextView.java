/**
 * 
 */
package com.japtar.chessclock.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	private final Rect mTextbounds = new Rect();
	public float x;
	public float y;
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
	
	/* ===========================================================
	 * Overrides
	 * =========================================================== */
	@Override
    protected void onDraw(Canvas canvas) {
		// Get the text to print
        final String text = super.getText().toString();
        final float textSize = super.getTextSize();
        final Typeface typeface = super.getTypeface();
        
        // setup stroke
        mStrokePaint.setColor(mOutlineColor);
        mStrokePaint.setTextSize(textSize);
        mStrokePaint.setStrokeWidth(textSize * OUTLINE_PROPORTION);
        mStrokePaint.setTypeface(typeface);
		
        // setup fill
        mFillPaint.setColor(super.getTextColors().getDefaultColor());
        mFillPaint.setTextSize(textSize);
        mFillPaint.setTypeface(typeface);
        
        // Figure out the drawing coordinates
        mFillPaint.getTextBounds(text, 0, text.length(), mTextbounds);
        final float originY = y - mTextbounds.height() * 0.5f;
        
        // draw everything
		canvas.drawText(text.toString(), x, originY, mStrokePaint);
		canvas.drawText(text.toString(), x, originY, mFillPaint);
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
