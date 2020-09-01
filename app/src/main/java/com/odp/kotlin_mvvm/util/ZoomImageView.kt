package com.odp.kotlin_mvvm.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author  ChenHh
 * @time   2020/9/1 15:46
 * @des
 **/

class ZoomImageView : AppCompatImageView, View.OnTouchListener {
    object ZoomMode {
        const val Ordinary = 0
        const val ZoomIn = 1
        const val TowFingerZoom = 2
    }


    private var mMatrix: Matrix? = null

    //imageView的大小
    private var viewSize: PointF? = null

    //图片的大小
    private var imageSize: PointF? = null

    //缩放后图片的大小
    private val scaleSize = PointF()

    //最初的宽高的缩放比例
    private val originScale = PointF()

    //imageview中bitmap的xy实时坐标
    private val bitmapOriginPoint = PointF()

    //点击的点
    private val clickPoint = PointF()

    //设置的双击检查时间限制
    private val doubleClickTimeSpan: Long = 250

    //上次点击的时间
    private var lastClickTime: Long = 0

    //双击放大的倍数
    private val doubleClickZoom = 2

    //当前缩放的模式
    private var zoomInMode = ZoomMode.Ordinary

    //临时坐标比例数据
    private val tempPoint = PointF()

    //最大缩放比例
    private val maxScrole = 4f

    //两点之间的距离
    private var doublePointDistance = 0f

    //双指缩放时候的中心点
    private val doublePointCenter = PointF()

    //两指缩放的比例
    private var doubleFingerScrole = 0f

    //上次触碰的手指数量
    private var lastFingerNum = 0


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)
        scaleType = ScaleType.MATRIX
        mMatrix = Matrix()
    }


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        viewSize = PointF(width.toFloat(), height.toFloat())
        val drawable = drawable
        if (drawable != null) {
            imageSize = PointF(
                drawable.minimumWidth.toFloat(),
                drawable.minimumHeight.toFloat()
            )
            showCenter()
        }
    }

    /**
     * 设置图片居中等比显示
     */
    private fun showCenter() {
        val scalex = viewSize!!.x / imageSize!!.x
        val scaley = viewSize!!.y / imageSize!!.y
        val scale = if (scalex < scaley) scalex else scaley
        scaleImage(PointF(scale, scale))

        //移动图片，并保存最初的图片左上角（即原点）所在坐标
        if (scalex < scaley) {
            translationImage(PointF(0F, viewSize!!.y / 2 - scaleSize.y / 2))
            bitmapOriginPoint.x = 0f
            bitmapOriginPoint.y = viewSize!!.y / 2 - scaleSize.y / 2
        } else {
            translationImage(PointF(viewSize!!.x / 2 - scaleSize.x / 2, 0F))
            bitmapOriginPoint.x = viewSize!!.x / 2 - scaleSize.x / 2
            bitmapOriginPoint.y = 0f
        }
        //保存下最初的缩放比例
        originScale[scale] = scale
        doubleFingerScrole = scale
    }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                //手指按下事件
                //记录被点击的点的坐标
                clickPoint[event.x] = event.y
                //判断屏幕上此时被按住的点的个数，当前屏幕只有一个点被点击的时候触发
                if (event.pointerCount == 1) {
                    //设置一个点击的间隔时长，来判断是不是双击
                    if (System.currentTimeMillis() - lastClickTime <= doubleClickTimeSpan) {
                        //如果图片此时缩放模式是普通模式，就触发双击放大
                        if (zoomInMode == ZoomMode.Ordinary) {
                            //分别记录被点击的点到图片左上角x,y轴的距离与图片x,y轴边长的比例，
                            //方便在进行缩放后，算出这个点对应的坐标点
                            tempPoint[(clickPoint.x - bitmapOriginPoint.x) / scaleSize.x] =
                                (clickPoint.y - bitmapOriginPoint.y) / scaleSize.y
                            //进行缩放
                            scaleImage(
                                PointF(
                                    originScale.x * doubleClickZoom,
                                    originScale.y * doubleClickZoom
                                )
                            )
                            //获取缩放后，图片左上角的xy坐标
                            getBitmapOffset()

                            //平移图片，使得被点击的点的位置不变。这里是计算缩放后被点击的xy坐标，
                            //与原始点击的位置的xy坐标值，计算出差值，然后做平移动作
                            translationImage(
                                PointF(
                                    clickPoint.x - (bitmapOriginPoint.x + tempPoint.x * scaleSize.x),
                                    clickPoint.y - (bitmapOriginPoint.y + tempPoint.y * scaleSize.y)
                                )
                            )
                            zoomInMode = ZoomMode.ZoomIn
                            doubleFingerScrole = originScale.x * doubleClickZoom
                        } else {
                            //双击还原
                            showCenter()
                            zoomInMode = ZoomMode.Ordinary
                        }
                    } else {
                        lastClickTime = System.currentTimeMillis()
                    }
                }
            }
            MotionEvent.ACTION_POINTER_DOWN ->                 //屏幕上已经有一个点按住 再按下一点时触发该事件
                //计算最初的两个手指之间的距离
                doublePointDistance = getDoubleFingerDistance(event)
            MotionEvent.ACTION_POINTER_UP -> {
                //屏幕上已经有两个点按住 再松开一点时触发该事件
                //当有一个手指离开屏幕后，就修改状态，这样如果双击屏幕就能恢复到初始大小
                zoomInMode = ZoomMode.ZoomIn
                //记录此时的双指缩放比例
                doubleFingerScrole = scaleSize.x / imageSize!!.x
                //记录此时屏幕触碰的点的数量
                lastFingerNum = 1
                //判断缩放后的比例，如果小于最初的那个比例，就恢复到最初的大小
                if (scaleSize.x < viewSize!!.x && scaleSize.y < viewSize!!.y) {
                    zoomInMode = ZoomMode.Ordinary
                    showCenter()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                //手指移动时触发事件
                /**************************************移动
                 */
                if (zoomInMode != ZoomMode.Ordinary) {
                    //如果是多指，计算中心点为假设的点击的点
                    var currentX = 0f
                    var currentY = 0f
                    //获取此时屏幕上被触碰的点有多少个
                    val pointCount = event.pointerCount
                    //计算出中间点所在的坐标
                    var i = 0
                    while (i < pointCount) {
                        currentX += event.getX(i)
                        currentY += event.getY(i)
                        i++
                    }
                    currentX /= pointCount.toFloat()
                    currentY /= pointCount.toFloat()
                    //当屏幕被触碰的点的数量变化时，将最新算出来的中心点看作是被点击的点
                    if (lastFingerNum != event.pointerCount) {
                        clickPoint.x = currentX
                        clickPoint.y = currentY
                        lastFingerNum = event.pointerCount
                    }
                    //将移动手指时，实时计算出来的中心点坐标，减去被点击点的坐标就得到了需要移动的距离
                    val moveX = currentX - clickPoint.x
                    val moveY = currentY - clickPoint.y
                    //计算边界，使得不能已出边界，但是如果是双指缩放时移动，因为存在缩放效果，
                    //所以此时的边界判断无效
                    val moveFloat = moveBorderDistance(moveX, moveY)
                    //处理移动图片的事件
                    translationImage(PointF(moveFloat[0], moveFloat[1]))
                    clickPoint[currentX] = currentY
                }
                /**************************************缩放
                 */
                //判断当前是两个手指接触到屏幕才处理缩放事件
                if (event.pointerCount == 2) {
                    //如果此时缩放后的大小，大于等于了设置的最大缩放的大小，就不处理
                    if ((scaleSize.x / imageSize!!.x >= originScale.x * maxScrole
                                || scaleSize.y / imageSize!!.y >= originScale.y * maxScrole)
                        && getDoubleFingerDistance(event) - doublePointDistance > 0
                    ) {

                    }
                    //这里设置当双指缩放的的距离变化量大于50，并且当前不是在双指缩放状态下，就计算中心点，等一些操作
                    if (Math.abs(getDoubleFingerDistance(event) - doublePointDistance) > 50
                        && zoomInMode != ZoomMode.TowFingerZoom
                    ) {
                        //计算两个手指之间的中心点，当作放大的中心点
                        doublePointCenter[(event.getX(0) + event.getX(1)) / 2] =
                            (event.getY(0) + event.getY(1)) / 2
                        //将双指的中心点就假设为点击的点
                        clickPoint.set(doublePointCenter)
                        //下面就和双击放大基本一样
                        getBitmapOffset()
                        //分别记录被点击的点到图片左上角x,y轴的距离与图片x,y轴边长的比例，
                        //方便在进行缩放后，算出这个点对应的坐标点
                        tempPoint[(clickPoint.x - bitmapOriginPoint.x) / scaleSize.x] =
                            (clickPoint.y - bitmapOriginPoint.y) / scaleSize.y
                        //设置进入双指缩放状态
                        zoomInMode = ZoomMode.TowFingerZoom
                    }
                    //如果已经进入双指缩放状态，就直接计算缩放的比例，并进行位移
                    if (zoomInMode == ZoomMode.TowFingerZoom) {
                        //用当前的缩放比例与此时双指间距离的缩放比例相乘，就得到对应的图片应该缩放的比例
                        val scrole =
                            doubleFingerScrole * getDoubleFingerDistance(event) / doublePointDistance
                        //这里也是和双击放大时一样的
                        scaleImage(PointF(scrole, scrole))
                        getBitmapOffset()
                        translationImage(
                            PointF(
                                clickPoint.x - (bitmapOriginPoint.x + tempPoint.x * scaleSize.x),
                                clickPoint.y - (bitmapOriginPoint.y + tempPoint.y * scaleSize.y)
                            )
                        )
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                //手指松开时触发事件
                lastFingerNum = 0
            }
        }
        return true
    }


    private fun scaleImage(scaleXY: PointF) {
        mMatrix!!.setScale(scaleXY.x, scaleXY.y)
        scaleSize[scaleXY.x * imageSize!!.x] = scaleXY.y * imageSize!!.y
        imageMatrix = mMatrix
    }

    /**
     * 对图片进行x和y轴方向的平移
     *
     * @param pointF
     */
    private fun translationImage(pointF: PointF) {
        mMatrix!!.postTranslate(pointF.x, pointF.y)
        imageMatrix = mMatrix
    }


    /**
     * 防止移动图片超过边界，计算边界情况
     *
     * @param moveX
     * @param moveY
     * @return
     */
    private fun moveBorderDistance(moveX: Float, moveY: Float): FloatArray {
        //计算bitmap的左上角坐标
        var moveX = moveX
        var moveY = moveY
        getBitmapOffset()

        //计算bitmap的右下角坐标
        val bitmapRightBottomX = bitmapOriginPoint.x + scaleSize.x
        val bitmapRightBottomY = bitmapOriginPoint.y + scaleSize.y
        if (moveY > 0) {
            //向下滑
            if (bitmapOriginPoint.y + moveY > 0) {
                moveY = if (bitmapOriginPoint.y < 0) {
                    -bitmapOriginPoint.y
                } else {
                    0f
                }
            }
        } else if (moveY < 0) {
            //向上滑
            if (bitmapRightBottomY + moveY < viewSize!!.y) {
                moveY = if (bitmapRightBottomY > viewSize!!.y) {
                    -(bitmapRightBottomY - viewSize!!.y)
                } else {
                    0f
                }
            }
        }
        if (moveX > 0) {
            //向右滑
            if (bitmapOriginPoint.x + moveX > 0) {
                moveX = if (bitmapOriginPoint.x < 0) {
                    -bitmapOriginPoint.x
                } else {
                    0f
                }
            }
        } else if (moveX < 0) {
            //向左滑
            if (bitmapRightBottomX + moveX < viewSize!!.x) {
                moveX = if (bitmapRightBottomX > viewSize!!.x) {
                    -(bitmapRightBottomX - viewSize!!.x)
                } else {
                    0f
                }
            }
        }
        return floatArrayOf(moveX, moveY)
    }

    /**
     * 获取view中bitmap的坐标点
     */
    private fun getBitmapOffset() {
        val value = FloatArray(9)
        val offset = FloatArray(2)
        val imageMatrix = imageMatrix
        imageMatrix.getValues(value)
        offset[0] = value[2]
        offset[1] = value[5]
        bitmapOriginPoint[offset[0]] = offset[1]
    }


    /**
     * 计算零个手指间的距离
     *
     * @param event
     * @return
     */
    private fun getDoubleFingerDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt(x * x + y * y.toDouble()).toFloat()
    }
}