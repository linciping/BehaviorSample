package com.linciping.behaviorsample;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {

    private static final int INVALID_POINTER=-1;

    @Nullable
    private Runnable flingRunnable;

    OverScroller scroller;

    private boolean isBeingDragged;
    private int activePointedId=INVALID_POINTER;
    private int lastMotionY;
    private int touchSlop=-1;

    @Nullable
    private VelocityTracker velocityTracker;

    public HeaderBehavior() {
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent ev) {
        if (touchSlop<0){
            touchSlop= ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }

        final int action=ev.getAction();

        if (action==MotionEvent.ACTION_MOVE&&isBeingDragged){
            return true;
        }

        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                isBeingDragged=false;
                final int x= (int) ev.getX();
                final int y= (int) ev.getY();
                break;
        }
    }
}
