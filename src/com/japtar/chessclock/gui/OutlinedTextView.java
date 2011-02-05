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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
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
	public int outlineColor = Color.TRANSPARENT;
	
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
    protected void onDraw(final Canvas canvas) {
		// Get the text to print
        final float textSize = super.getTextSize();
        final String text = super.getText().toString();
        
        // setup stroke
        mStrokePaint.setColor(outlineColor);
        mStrokePaint.setStrokeWidth(textSize * OUTLINE_PROPORTION);
        mStrokePaint.setTextSize(textSize);
        mStrokePaint.setFlags(super.getPaintFlags());
        mStrokePaint.setTypeface(super.getTypeface());
        
        // Figure out the drawing coordinates
        super.getPaint().getTextBounds(text, 0, text.length(), mTextBounds);
        
        // draw everything
		canvas.drawText(text,
				super.getWidth() * 0.5f, (super.getHeight() + mTextBounds.height()) * 0.5f,
				mStrokePaint);
		super.onDraw(canvas);
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
        outlineColor = array.getColor(
        		R.styleable.OutlinedTextView_outlineColor, 0x00000000);
        array.recycle(); 
		
		// Force this text label to be centered
		super.setGravity(Gravity.CENTER_HORIZONTAL);
	}
}
