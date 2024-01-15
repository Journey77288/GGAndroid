package io.ganguo.library.core.event.extend;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 手势适配器
 * <p/>
 * Created by zhihui_chen on 14-9-29.
 */
public class GestureAdapter implements GestureDetector.OnGestureListener {
    public static final String TAG = GestureAdapter.class.getName();
    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 0;

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float up = e1.getY() - e2.getY();
        float bottom = e2.getY() - e1.getY();
        float left = e1.getX() - e2.getX();
        float right = e2.getX() - e1.getX();

        Log.d(TAG, "onFling up: " + up + " bottom: " + bottom + " left: " + left + " right: " + right);

        if (up > bottom && up > left && up > right) {
            // 向下手势
            return onFlingUp(e1, e2, velocityX, velocityY);
        } else if (bottom > up && bottom > left && bottom > right) {
            // 向上手势
            return onFlingBottom(e1, e2, velocityX, velocityY);
        } else if (left > up && left > bottom && left > right) {
            // 向左手势
            return onFlingLeft(e1, e2, velocityX, velocityY);
        } else if (right > up && right > bottom && right > left) {
            // 向右手势
            return onFlingRight(e1, e2, velocityX, velocityY);
        }

//        if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
//            // 向下手势
//            return onFlingUp(e1, e2, velocityX, velocityY);
//        } else if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
//            // 向上手势
//            return onFlingBottom(e1, e2, velocityX, velocityY);
//        } else if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
//            // 向左手势
//            return onFlingLeft(e1, e2, velocityX, velocityY);
//        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
//            // 向右手势
//            return onFlingRight(e1, e2, velocityX, velocityY);
//        }
        return false;
    }

    public boolean onFlingLeft(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFlingLeft");
        return false;
    }

    public boolean onFlingRight(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFlingRight");
        return false;
    }

    public boolean onFlingUp(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFlingUp");
        return false;
    }

    public boolean onFlingBottom(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFlingBottom");
        return false;
    }
}
