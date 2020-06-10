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
            case MotionEvent.ACTION_DOWN: {
                isBeingDragged = false;
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    lastMotionY = y;
                    this.activePointedId = ev.getPointerId(0);
                    ensureVelocityTracker();
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                final int activePointerId = this.activePointedId;
                if (activePointerId == INVALID_POINTER) {
                    break;
                }
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) {
                    break;
                }
                final int y = (int) ev.getY(pointerIndex);
                final int yDiff = Math.abs(y - lastMotionY);
                if (yDiff > touchSlop) {
                    isBeingDragged = true;
                    lastMotionY = y;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                isBeingDragged = false;
                this.activePointedId = INVALID_POINTER;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
            }
        }
        if (velocityTracker!=null){
            velocityTracker.addMovement(ev);
        }
        return isBeingDragged;
    }

    boolean canDragView(V view) {
        return false;
    }

    private void ensureVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }
}
