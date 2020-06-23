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

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Rvh item touch helper callback.
 */
public class RVHItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final boolean isItemViewSwipeEnabledLeft;

    private final boolean isItemViewSwipeEnabledRight;

    private final boolean isLongPressDragEnabled;

    private final RVHAdapter mAdapter;

    /**
     * Instantiates a new Rvh item touch helper callback.
     *
     * @param adapter                     the adapter
     * @param isLongPressDragEnabled      the is long press drag enabled
     * @param isItemViewSwipeEnabledLeft  the is item view swipe enabled left
     * @param isItemViewSwipeEnabledRight the is item view swipe enabled right
     */
    public RVHItemTouchHelperCallback(final RVHAdapter adapter, final boolean isLongPressDragEnabled,
                                      final boolean isItemViewSwipeEnabledLeft, final boolean isItemViewSwipeEnabledRight) {
        this.mAdapter = adapter;
        this.isItemViewSwipeEnabledLeft = isItemViewSwipeEnabledLeft;
        this.isItemViewSwipeEnabledRight = isItemViewSwipeEnabledRight;
        this.isLongPressDragEnabled = isLongPressDragEnabled;
    }

    @Override
    public boolean canDropOver(final RecyclerView recyclerView, final RecyclerView.ViewHolder current,
                               final RecyclerView.ViewHolder target) {
        return current.getItemViewType() == target.getItemViewType();
    }

    @Override
    public void clearView(final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof RVHViewHolder) {
            // Tell the view holder it's time to restore the idle state
            final RVHViewHolder itemViewHolder = (RVHViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }

    @Override
    public int getMovementFlags(final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags;
        if (this.isItemViewSwipeEnabledLeft && this.isItemViewSwipeEnabledRight) {
            swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        } else if (this.isItemViewSwipeEnabledRight) {
            swipeFlags = ItemTouchHelper.START;
        } else {
            swipeFlags = ItemTouchHelper.END;
        }

        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return this.isItemViewSwipeEnabledLeft || this.isItemViewSwipeEnabledRight;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return this.isLongPressDragEnabled;
    }

    @Override
    public void onChildDraw(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder,
                            final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        if (ItemTouchHelper.ACTION_STATE_SWIPE == actionState) {
            // Fade out the view as it is swiped out of the parent's bounds
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public boolean onMove(final RecyclerView recyclerView, final RecyclerView.ViewHolder source,
                          final RecyclerView.ViewHolder target) {
        // Notify the adapter of the move
        this.mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(final RecyclerView.ViewHolder viewHolder, final int actionState) {
        // We only want the active item to change
        if ((ItemTouchHelper.ACTION_STATE_IDLE != actionState) && (viewHolder instanceof RVHViewHolder)) {
            // Let the view holder know that this item is being moved or dragged
            final RVHViewHolder itemViewHolder = (RVHViewHolder) viewHolder;
            itemViewHolder.onItemSelected(actionState);
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
        this.mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), direction);
    }
}
