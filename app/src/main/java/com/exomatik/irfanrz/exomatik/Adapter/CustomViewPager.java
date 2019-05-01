package com.exomatik.irfanrz.exomatik.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by IrfanRZ on 8/5/2018.
 */

public class CustomViewPager extends ViewPager {
    private boolean scrollEnable = true;            // horizontal scrolling

    public CustomViewPager(Context ctx){            // public so that can be accessed outside the package
        super(ctx);
    }

    public CustomViewPager(Context ctx, AttributeSet attrs){            // constructor so that this view can also be accessed from xml layout
        super(ctx, attrs);
    }

    public void setScrollEnable(boolean enable){
        scrollEnable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
