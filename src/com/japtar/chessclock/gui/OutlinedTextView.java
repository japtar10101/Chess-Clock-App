/**
 * 
 */
package com.japtar.chessclock.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
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
	private static final Point ORIGIN = new Point();
	
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
        
        // setup stroke
        mStrokePaint.setColor(mOutlineColor);
        mStrokePaint.setStrokeWidth(textSize * OUTLINE_PROPORTION);
        mStrokePaint.setTextSize(textSize);
        mStrokePaint.setFlags(super.getPaintFlags());
        mStrokePaint.setTypeface(super.getTypeface());
        
        // Figure out the drawing coordinates
        this.updateBounds();
        
        // draw everything
		canvas.drawText(super.getText().toString(),
				mTextBounds.centerX(), mTextBounds.centerY(),
				mStrokePaint);
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
		// Reset Text Bounds to default positions
		mTextBounds.set(0, 0, 0, 0);
		
		// Grab the current child and parent
		View child = this;
		ViewParent parent = super.getParent();
		
		// Grab the bounds of each 
		while((parent != null) && (child != null) && (parent != child)) {
			// Grab the coordinates into a temporary rectangle
			parent.getChildVisibleRect(child, mTempBounds, ORIGIN);
			
			// Increment the boundary coordinates
			mTextBounds.left += mTempBounds.left;
			mTextBounds.top += mTempBounds.top;
			mTextBounds.right += mTempBounds.right;
			mTextBounds.bottom += mTempBounds.bottom;
			
			// Attempt to convert the parent into a View
			child = null;
			if(parent instanceof View) {
				child = (View) parent;
			}
			
			// Get the next parent in the hierarchy
			parent = parent.getParent();
		}
		
		// Force this text label to be centered
		super.setGravity(Gravity.CENTER_HORIZONTAL);
	}
}
