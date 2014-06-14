package com.kartikhariharan.prayerbookapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

public class ExpandAnimation extends Animation {
	private View mAnimatedView;
    private LayoutParams mViewLayoutParams;
    private int mMarginStart, mMarginEnd;
    private boolean mIsVisibleAfter = false;
    private boolean mWasEndedAlready = false;
	
	public ExpandAnimation(View view, int duration) {

        setDuration(duration);
        mAnimatedView = view;
        mViewLayoutParams = (LayoutParams) view.getLayoutParams();

        // decide to show or hide the view
        mIsVisibleAfter = (view.getVisibility() == View.VISIBLE);

        mMarginStart = mViewLayoutParams.bottomMargin;
        mMarginEnd = (mMarginStart == 0 ? (0- view.getHeight()) : 0);

        view.setVisibility(View.VISIBLE);
	}

	public ExpandAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		
		 if (interpolatedTime < 1.0f) {

	            // Calculating the new bottom margin, and setting it
	            mViewLayoutParams.bottomMargin = mMarginStart
	                    + (int) ((mMarginEnd - mMarginStart) * interpolatedTime);

	            // Invalidating the layout, making us seeing the changes we made
	            mAnimatedView.requestLayout();

	        // Making sure we didn't run the ending before (it happens!)
	        } else if (!mWasEndedAlready) {
	            mViewLayoutParams.bottomMargin = mMarginEnd;
	            mAnimatedView.requestLayout();

	            if (mIsVisibleAfter) {
	                mAnimatedView.setVisibility(View.GONE);
	            }
	            mWasEndedAlready = true;
	        }
	}
	
	

}
