package com.odp.kotlin_mvvm.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  ChenHh
 * @time   2020/8/18 15:33
 * @des
 **/
class RecyclerItemDecoration(space: Int, num: Int) : RecyclerView.ItemDecoration() {
    private var itemSpace = 0
    private var itemNum = 0

    init {
        itemSpace = space
        itemNum = num
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view!!, parent, state!!)
        outRect.bottom = itemSpace * 2

        //parent.getChildLayoutPosition(view) 获取view的下标
        if (parent.getChildLayoutPosition(view) % itemNum == 0) {
            outRect.left = 0
            if (itemNum == 1 || itemNum == 3) {
                outRect.right = 0
            } else {
                outRect.right = itemSpace
            }
        } else {
            outRect.left = itemSpace
            outRect.right = 0
        }
    }

}