package com.odp.kotlin_mvvm.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet


import androidx.appcompat.widget.AppCompatImageView
import com.odp.kotlin_mvvm.R
import kotlin.math.max


/**
 * @author  ChenHh
 * @time   2020/9/17 16:50
 * @des
 **/
class CustomRoundImageView : AppCompatImageView {
    private var radius: Int = 0
    private var leftBottomRadius: Int = 0
    private var rightBottomRadius: Int = 0
    private var rightTopRadius: Int = 0
    private var leftTopRadius: Int = 0
    private val defaultRadius: Int = 0
    private var widthView = 0f
    private var heightView = 0f

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(context: Context, attrs: AttributeSet?) {
        val array: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Custom_Round_Image_View)
        radius =
            array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_radius, defaultRadius)
        leftTopRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_left_top_radius,
            defaultRadius
        )
        rightTopRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_right_top_radius,
            defaultRadius
        )
        rightBottomRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_right_bottom_radius,
            defaultRadius
        )
        leftBottomRadius = array.getDimensionPixelOffset(
            R.styleable.Custom_Round_Image_View_left_bottom_radius,
            defaultRadius
        )

        if (defaultRadius == leftTopRadius) {
            this.leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            this.rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            this.rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            this.leftBottomRadius = radius
        }
        array.recycle()
    }

    fun setAllRadius(radius: Int) {
        this.leftTopRadius = radius
        this.rightTopRadius = radius
        this.rightBottomRadius = radius
        this.leftBottomRadius = radius
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        val maxLeft: Int = max(leftTopRadius, leftBottomRadius)
        val maxRight: Int = max(rightTopRadius, rightBottomRadius)
        val minWidth = maxLeft + maxRight
        val maxTop: Int = max(leftTopRadius, rightTopRadius)
        val maxBottom: Int = max(leftBottomRadius, rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (widthView >= minWidth && heightView > minHeight) {
            val path = Path()
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius.toFloat(), 0F)
            path.lineTo(widthView - rightTopRadius, 0F)
            path.quadTo(widthView, 0F, widthView, rightTopRadius.toFloat())
            path.lineTo(widthView, heightView - rightBottomRadius)
            path.quadTo(widthView, heightView, widthView - rightBottomRadius, heightView)
            path.lineTo(leftBottomRadius.toFloat(), heightView)
            path.quadTo(0F, heightView, 0F, heightView - leftBottomRadius)
            path.lineTo(0F, leftTopRadius.toFloat())
            path.quadTo(0F, 0F, leftTopRadius.toFloat(), 0F)
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        widthView = width.toFloat()
        heightView = height.toFloat()
    }
}