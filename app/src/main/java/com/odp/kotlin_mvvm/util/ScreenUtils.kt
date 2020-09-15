package com.odp.kotlin_mvvm.util

import android.content.Context
import kotlin.math.roundToInt

/**
 * @author  ChenHh
 * @time   2020/9/15 17:30
 * @des
 **/
public class ScreenUtils {

    private var screenWidth: Int = 0

    fun getScreenWidth(context: Context): Int {
        if (screenWidth == 0) {
            screenWidth = context.resources.displayMetrics.widthPixels
        }
        return screenWidth
    }

    fun dpToPxInt(context: Context?, dp: Float): Int {

        return ((ScreenUtils().dpToPx(context, dp) + 0.5f).roundToInt())
    }

     fun dpToPx(context: Context?, dp: Float): Float {
        return if (context == null) {
            (-1).toFloat()
        } else dp * context.resources.displayMetrics.density
    }
}