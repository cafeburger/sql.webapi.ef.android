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

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;

/**
 * The type Rvh item click listener.
 */
public class RVHItemClickListener implements OnItemTouchListener {

    /**
     * The M gesture detector.
     */
    private final GestureDetector mGestureDetector;
    private final OnItemClickListener mListener;

    /**
     * Instantiates a new Rvh item click listener.
     *
     * @param context  the context
     * @param listener the listener
     */
    public RVHItemClickListener(final Context context, final OnItemClickListener listener) {
        this.mListener = listener;
        this.mGestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(final MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(final RecyclerView view, final MotionEvent e) {
        final View childView = view.findChildViewUnder(e.getX(), e.getY());
        if ((null != childView) && (null != mListener) && this.mGestureDetector.onTouchEvent(e)) {
            this.mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {
        // Do nothings
    }

    @Override
    public void onTouchEvent(final RecyclerView view, final MotionEvent motionEvent) {
        // Do nothing
    }

    /**
     * The interface On item click listener.
     */
    public interface OnItemClickListener {

        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }
}
