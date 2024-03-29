package com.app.fastcab.ui.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created on 7/12/2017.
 */

public class ExpandedBottomSheetBehavior<V extends View> extends android.support.design.widget.BottomSheetBehavior<V> {
    private boolean mAllowUserDragging = true;

    public ExpandedBottomSheetBehavior() {
        super();
    }

    public ExpandedBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setAllowUserDragging(boolean allowUserDragging) {
        mAllowUserDragging = allowUserDragging;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        return event != null && child != null && parent != null && mAllowUserDragging && super.onInterceptTouchEvent(parent, child, event);
    }

    @Override
    public boolean onLayoutChild(final CoordinatorLayout parent, final V child, final int layoutDirection) {
        SavedState dummySavedState = new SavedState(super.onSaveInstanceState(parent, child), STATE_EXPANDED);
        super.onRestoreInstanceState(parent, child, dummySavedState);
        return super.onLayoutChild(parent, child, layoutDirection);
        /*
            Unfortunately its not good enough to just call setState(STATE_EXPANDED); after super.onLayoutChild
            The reason is that an animation plays after calling setState. This can cause some graphical issues with other layouts
            Instead we need to use setInternalState, however this is a private method.
            The trick is to utilise onRestoreInstance to call setInternalState immediately and indirectly
         */
    }

}