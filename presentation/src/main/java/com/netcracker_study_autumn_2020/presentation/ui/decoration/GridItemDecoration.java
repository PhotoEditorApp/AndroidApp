package com.netcracker_study_autumn_2020.presentation.ui.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int gridSpacingPx;
    private int gridSize;

    private boolean mNeedLeftSpacing;

    public GridItemDecoration(int gridSpacingPx, int gridSize) {
        this.gridSpacingPx = gridSpacingPx;
        this.gridSize = gridSize;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth()- (float)gridSpacingPx * (gridSize - 1)) / gridSize);
        int padding = parent.getWidth() / gridSize - frameWidth;
        int itemPosition = ((RecyclerView.LayoutParams)view.getLayoutParams() ).getViewAdapterPosition();
        if (itemPosition < gridSize) {
            outRect.top = 0;
        } else {
            outRect.top = gridSpacingPx;
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            mNeedLeftSpacing = true;
        } else if ((itemPosition + 1) % gridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false;
            outRect.left = gridSpacingPx - padding;
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = gridSpacingPx - padding;
            } else {
                outRect.right = gridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.left = gridSpacingPx / 2;
            outRect.right = gridSpacingPx - padding;
        } else {
            mNeedLeftSpacing = false;
            outRect.left = gridSpacingPx / 2;
            outRect.right = gridSpacingPx / 2;
        }
        outRect.bottom = 0;
    }
}

