package com.odp.kotlin_mvvm.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  ChenHh
 * @time   2021/3/29 15:33
 * @des
 **/
class HotMovieItemDecoration(space: Int) : RecyclerView.ItemDecoration() {
    private var itemSpace = 0

    init {
        itemSpace = space
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemCount = parent.adapter?.itemCount

        outRect.right = itemSpace
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left = itemSpace * 2
        }
        if (parent.getChildLayoutPosition(view) == (itemCount?.minus(
                1
            ))
        ) {
            outRect.right = itemSpace * 2

        }

    }
}