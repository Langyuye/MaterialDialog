package com.langyuye.library.dialog.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.langyuye.library.R;

public class RippleTextView extends android.support.v7.widget.AppCompatTextView {
    /*起始点*/
    private int mInitX;
    private int mInitY;

    private float mCurrentX;
    private float mCurrentY;

    /*绘制的半径*/
    private float mRadius;
    private float mStepRadius;
    private float mStepOriginX;
    private float mStepOriginY;
    private float mDrawRadius;

    private boolean mDrawFinish;

    private int pressedColor;
    private int rippleColor;

    private final int DURATION = 200;
    private final int FREQUENCY = 10;
    private float mCycle;
    private final Rect mRect = new Rect();

    private boolean mPressUp = false;

    private Paint mRevealPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RippleTextView(Context context) {
        super(context);
        initView(context,null,0);
    }

    public RippleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs,0);
    }

    public RippleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }
    private void initView(Context context,AttributeSet attrs,int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ripple_view);
        pressedColor=a.getColor(R.styleable.ripple_view_pressed_color,0x10000000);
        rippleColor=a.getColor(R.styleable.ripple_view_ripple_color,0x10000000);
        mRevealPaint.setColor(rippleColor);
        mCycle = DURATION / FREQUENCY;
        final float density = getResources().getDisplayMetrics().density ;
        mCycle = (density*mCycle);
        mDrawFinish = true;
    }
    public void setPressedColor(int resId){
        pressedColor=resId;
    }
    public void setRippleColor(int resId){
        mRevealPaint.setColor(resId);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawFinish) {
            super.onDraw(canvas);
            return;
        }
        canvas.drawColor(pressedColor);
        super.onDraw(canvas);
        if (mStepRadius == 0) {
            return;
        }
        mDrawRadius = mDrawRadius + mStepRadius;
        mCurrentX = mCurrentX + mStepOriginX;
        mCurrentY = mCurrentY + mStepOriginY;
        if (mDrawRadius > mRadius) {
            mDrawRadius = 0;
            canvas.drawCircle(mRect.width() / 2, mRect.height() / 2, mRadius, mRevealPaint);
            mDrawFinish = true;
            if (mPressUp)
                invalidate();
            return;
        }

        canvas.drawCircle(mCurrentX, mCurrentY, mDrawRadius, mRevealPaint);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    private void updateDrawData() {
        /*最大半径*/
        mRadius = (float) Math.sqrt(mRect.width() / 2 * mRect.width() / 2 + mRect.height() / 2 * mRect.height() / 2);
        /*半径的偏移量*/
        mStepRadius = mRadius / mCycle;
        /*圆心X的偏移量*/
        mStepOriginX = (mRect.width() / 2 - mInitX) / mCycle;
        /*圆心Y的偏移量*/
        mStepOriginY = (mRect.height() / 2 - mInitY) / mCycle;

        mCurrentX = mInitX;
        mCurrentY = mInitY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                    mPressUp = false;
                    mDrawFinish = false;
                    int index = MotionEventCompat.getActionIndex(event);
                    int eventId = MotionEventCompat.getPointerId(event, index);
                    if (eventId != -1) {
                        mInitX = (int) MotionEventCompat.getX(event, index);
                        mInitY = (int) MotionEventCompat.getY(event, index);
                        updateDrawData();
                        invalidate();
                    }
                    break;
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mStepRadius = (int) (5 * mStepRadius);
                mStepOriginX = (int) (5 * mStepOriginX);
                mStepOriginY = (int) (5 * mStepOriginY);
                mPressUp = true;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private int getMax(int... radius) {
        if (radius.length == 0) {
            return 0;
        }
        int max = radius[0];
        for (int m : radius) {
            if (m > max) {
                max = m;
            }
        }
        return max;
    }

    @Override
    public boolean performClick() {

        postDelayed(new Runnable() {
                @Override
                public void run() {
                    RippleTextView.super.performClick();
                }
            }, 150);
        return true;

    }
}
