package com.custom.relish.font.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.custom.relish.font.CustomFontApp
import com.custom.relish.font.R
import kotlin.math.min
import kotlin.math.sqrt


/**
 *author: hss
 *date: 2024/5/15 16:33
 *class desc:
 **/
class FontSwitchView : View, View.OnTouchListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    private val fontPaint = Paint()
    private var viewWidth = 0
    private var viewHeight = 0

    private val roundRect = RectF()

    private var bgCircleX = 0f

    private var isFontSelected = true  //默认选中font 字体

    private var isClickCall: ((isLeftClick: Boolean) -> Unit)? = null

    private var valueAnimator: ValueAnimator? = null

    private var isClickLeft: Boolean = false

    private var eventStartX = 0f
    private var eventStartY = 0f
    private var totalDistance: Float = 0f


    private val drawableFont: Drawable? by lazy {
        ContextCompat.getDrawable(
            context,
            R.drawable.ic_font_iv
        )
    }
    private val drawableSmile: Drawable? by lazy {
        ContextCompat.getDrawable(
            context,
            R.drawable.ic_smile_face_iv
        )
    }

    init {
        fontPaint.let {
            it.color = context.getColor(R.color.color_D9D9D9)
            it.isAntiAlias = true
            it.style = Paint.Style.FILL
        }
        post {
            setOnTouchListener(this)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getMode(widthMeasureSpec).let {
            if (it == MeasureSpec.EXACTLY) {
                MeasureSpec.getSize(widthMeasureSpec)
            } else {
                CustomFontApp.instances.dp2px(88f)
            }
        }
        val heightSize = MeasureSpec.getMode(heightMeasureSpec).let {
            if (it == MeasureSpec.EXACTLY) {
                MeasureSpec.getSize(heightMeasureSpec)
            } else {
                CustomFontApp.instances.dp2px(44f)
            }
        }
        val heightValue = min(widthSize, heightSize)
        setMeasuredDimension(heightValue * 2, heightValue)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        viewWidth = measuredWidth
        viewHeight = measuredHeight

        bgCircleX = viewWidth * if (isFontSelected) 0.25f else 0.75f
    }

    fun setClickCall(isClickCall: ((isLeftClick: Boolean) -> Unit)) {
        this.isClickCall = isClickCall
    }

    fun updateSwitchLayout(vpIndex: Int) {
        if (isClickLeft == (vpIndex == 0))return
        isClickLeft = vpIndex == 0
        dealClick(false)
    }

    private fun updateSelectedAnimationLayout(endCall: (() -> Unit)? = null) {
        if (valueAnimator != null) {
            valueAnimator!!.cancel()
            valueAnimator = null
        }

        valueAnimator = ValueAnimator.ofFloat(
            if (isFontSelected) viewWidth * 0.75f else viewWidth * 0.25f,
            if (isFontSelected) viewWidth * 0.25f else viewWidth * 0.75f
        ).apply {
            duration = 120
            interpolator = LinearInterpolator()
            addUpdateListener {
                bgCircleX = it.animatedValue as Float
                postInvalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    endCall?.invoke()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
            start()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        if (viewWidth == 0 || viewHeight == 0) return
        if (drawableFont == null || drawableSmile == null) return

        /*绘制背景*/
        fontPaint.color = context.getColor(R.color.color_D9D9D9)
        roundRect.set(0.0f, 0.0f, viewWidth.toFloat(), viewHeight.toFloat())
        val radiusFloat = viewHeight.toFloat() * 0.5f
        canvas.drawRoundRect(roundRect, radiusFloat, radiusFloat, fontPaint)

        /*绘制选中的圆*/
        fontPaint.color = context.getColor(R.color.color_703EFF)
        canvas.drawCircle(bgCircleX, radiusFloat, radiusFloat, fontPaint)

        /*绘制两个icon*/
        drawableFont?.let {
            // 计算 Drawable 的宽度和高度
            val drawableWidth: Int = it.intrinsicWidth
            val drawableHeight: Int = it.intrinsicHeight
            // 计算绘制的位置
            val left = (viewWidth * 0.25f - drawableWidth * 0.5f).toInt()
            val top: Int = (viewHeight - drawableHeight) / 2
            val right: Int = left + drawableWidth
            val bottom: Int = top + drawableHeight
            it.setBounds(left, top, right, bottom)
            it.draw(canvas)
        }

        drawableSmile?.let {
            // 计算 Drawable 的宽度和高度
            val drawableWidth: Int = it.intrinsicWidth
            val drawableHeight: Int = it.intrinsicHeight
            // 计算绘制的位置
            val left = (viewWidth * 0.75f - drawableWidth * 0.5f).toInt()
            val top: Int = (viewHeight - drawableHeight) / 2
            val right: Int = left + drawableWidth
            val bottom: Int = top + drawableHeight
            it.setBounds(left, top, right, bottom)
            it.draw(canvas)
        }

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        event?.let {

            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    eventStartX = it.x
                    eventStartY = it.y
                    isClickLeft = eventStartX < viewWidth * 0.5f
                    totalDistance = 0f
                }

                MotionEvent.ACTION_MOVE -> {
                    val dx = event.x - eventStartX
                    val dy = event.y - eventStartY
                    totalDistance += sqrt((dx * dx + dy * dy).toDouble()).toFloat()
                }

                MotionEvent.ACTION_UP -> {
                    // 判断总距离是否小于阈值，如果小于阈值则认为是一个 click 事件
                    if (totalDistance < 10) {
                        dealClick(true)
                    }
                }
            }
        }
        return true
    }

    private fun dealClick(needCall: Boolean) {
        if (isClickLeft) {
            if (isFontSelected) return
            isFontSelected = true
            //needCall
            if (needCall) {
                updateSelectedAnimationLayout {
                    isClickCall?.invoke(true)
                }
            } else {
                updateSelectedAnimationLayout()
            }
        } else {
            if (!isFontSelected) return
            isFontSelected = false

            //needCall
            if (needCall) {
                updateSelectedAnimationLayout {
                    isClickCall?.invoke(false)
                }
            } else {
                updateSelectedAnimationLayout()
            }
        }
    }


}