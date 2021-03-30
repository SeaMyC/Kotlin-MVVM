package com.odp.kotlin_mvvm.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  ChenHh
 * @time   2021/3/29 15:33
 * @des
 **/
class DiscoverItemDecoration(space: Int) : RecyclerView.ItemDecoration() {
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
        outRect.bottom = itemSpace
    }
}