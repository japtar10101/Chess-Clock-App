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
import android.view.View;
import android.view.ViewParent;
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
	private final Paint mStrokePaint = new Paint();
	private final Rect mTextBounds = new Rect();
	private final Rect mTempBounds = new Rect();
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
        final float textSize = super.getTextSize();
        final Typeface typeface = super.getTypeface();
        
        // setup stroke
        mStrokePaint.setColor(mOutlineColor);
        mStrokePaint.setStrokeWidth(textSize * OUTLINE_PROPORTION);
        mStrokePaint.setFlags(super.getPaintFlags());
        mStrokePaint.setTextSize(super.getTextSize());
        mStrokePaint.setTypeface(super.getTypeface());
		
        // Figure out the drawing coordinates
        this.updateBounds();
        
        // draw everything
		canvas.drawText(super.getText().toString(),
				mTextBounds.exactCenterX(), mTextBounds.exactCenterY(),
				mStrokePaint);
//		canvas.drawText(text.toString(), originX, originY, mFillPaint);
		super.onDraw(canvas);
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
		mStrokePaint.setAntiAlias(true);
		mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setTextAlign(Paint.Align.CENTER);
	}
	private final void setupAttributes(Context context, AttributeSet attrs) {
		final TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.OutlinedTextView);
        mOutlineColor = array.getColor(
        		R.styleable.OutlinedTextView_outlineColor, 0x00000000);
        array.recycle(); 
	}
	private final void updateBounds() {
		final View root = super.getRootView();
		ViewParent tempParent = super.getParent();
		while(tempParent != null) {
			
		}
	}
}
