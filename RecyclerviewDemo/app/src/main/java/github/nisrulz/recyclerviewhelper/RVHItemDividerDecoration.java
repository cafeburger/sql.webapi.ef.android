/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.nisrulz.recyclerviewhelper;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Rvh item divider decoration.
 */
public class RVHItemDividerDecoration extends RecyclerView.ItemDecoration {

    /**
     * The constant HORIZONTAL_LIST.
     */
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    /**
     * The constant VERTICAL_LIST.
     */
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private static final int[] ATTRS = {
            R.attr.listDivider
    };

    private final Drawable mDivider;

    private int mOrientation = 0;

    /**
     * Instantiates a new Rvh item divider decoration.
     *
     * @param context     the context
     * @param orientation the orientation
     */
    public RVHItemDividerDecoration(final Context context, final int orientation) {
        final TypedArray a = context.obtainStyledAttributes(RVHItemDividerDecoration.ATTRS);
        this.mDivider = a.getDrawable(0);
        a.recycle();
        this.setOrientation(orientation);
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent,
                               final RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
        }
    }

    @Override
    public void onDraw(final Canvas c, final RecyclerView parent, final RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mOrientation == VERTICAL_LIST) {
            this.drawVertical(c, parent);
        } else {
            this.drawHorizontal(c, parent);
        }
    }

    /**
     * Sets orientation.
     *
     * @param orientation the orientation
     */
    public void setOrientation(int orientation) {
        if ((orientation != HORIZONTAL_LIST)
                && (orientation != VERTICAL_LIST)) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = orientation;
    }

    /**
     * Draw horizontal.
     *
     * @param c      the c
     * @param parent the parent
     */
    private void drawHorizontal(final Canvas c, final RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin + Math.round(child.getTranslationX());
            final int right = left + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }
    }

    /**
     * Draw vertical.
     *
     * @param c      the c
     * @param parent the parent
     */
    private void drawVertical(final Canvas c, final RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + Math.round(child.getTranslationY());
            final int bottom = top + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }
    }
}