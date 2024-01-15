package io.ganguo.demo.view.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.ganguo.utils.util.GridPositions;


/**
 * Created by leo on 16/7/22.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int rowCount;

    public SpacesItemDecoration(int space, int lineCount) {
        this.space = space;
        this.rowCount = lineCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Add top margin only for the first item to avoid double space between items
        int position = parent.getChildLayoutPosition(view);
        int size = parent.getAdapter().getItemCount();
        if (GridPositions.isLeft(position, rowCount)) {
            outRect.left = space * 2;
        } else {
            outRect.left = space;
        }
        if (GridPositions.isRight(position, rowCount)) {
            outRect.right = space * 2;
        } else {
            outRect.right = space;
        }
        if (GridPositions.isFirstLine(position, rowCount)) {
            outRect.top = space * 2;
        } else {
            outRect.top = space;
        }
        if (GridPositions.isLastLine(position, size, rowCount)) {
            outRect.bottom = space * 2;
        } else {
            outRect.bottom = space;
        }
    }
}

