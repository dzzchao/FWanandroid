package com.dzzchao.fwanandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Dimension
import com.dzzchao.fwanandroid.R
import timber.log.Timber

/**
 * 自定义标题栏
 *
 * 1 可以设置标题，文字颜色
 * 2 可以设置左右按钮图片和点击事件
 */
class TitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var titleText = "Title"
    private var titleTextColor = Color.BLACK
    private var titleTextSize = 16F
    private var leftButtonBackground = 0x00
    private var rightButtonBackground = 0x00
    private var leftButtonText = ""
    private var rightButtonText = ""
    private var isHideLeftButton = true
    private var isHideRightButton = true

    lateinit var leftClickListener: OnLeftClickListener
    lateinit var rightClickListener: OnRightClickListener

    interface OnLeftClickListener {
        fun click()
    }

    interface OnRightClickListener {
        fun click()
    }


    init {
        context.obtainStyledAttributes(attrs, R.styleable.TitleBar).run {
            titleText = getString(R.styleable.TitleBar_titleText).toString()
            titleTextColor = getColor(R.styleable.TitleBar_titleColor, Color.BLACK)
            titleTextSize = getDimension(R.styleable.TitleBar_titleSize, 16F)
            leftButtonBackground = getResourceId(R.styleable.TitleBar_leftButtonBackground, 0)
            rightButtonBackground = getResourceId(R.styleable.TitleBar_rightButtonBackground, 0)
            leftButtonText = getString(R.styleable.TitleBar_leftButtonText).toString()
            rightButtonText = getString(R.styleable.TitleBar_rightButtonText).toString()
            isHideLeftButton = getBoolean(R.styleable.TitleBar_hideLeftButton, true)
            isHideRightButton = getBoolean(R.styleable.TitleBar_hideRightButton, true)
            recycle()
        }

        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.titilbar, null)
        val tvTitle = view.findViewById<TextView>(R.id.tBtvTitle)
        val tvLeft = view.findViewById<TextView>(R.id.tBtextLeft)
        val tvRight = view.findViewById<TextView>(R.id.tBtextRight)
        val ivLeft = view.findViewById<ImageView>(R.id.tBivLeft)
        val ivRight = view.findViewById<ImageView>(R.id.tBiVRight)
        val leftLayout = view.findViewById<RelativeLayout>(R.id.tBleftLayout)
        val rightLayout = view.findViewById<RelativeLayout>(R.id.tBrightLayout)

        ivLeft.visibility = View.GONE
        ivRight.visibility = View.GONE
        tvLeft.visibility = View.GONE
        tvRight.visibility = View.GONE

        leftLayout.isClickable = false
        rightLayout.isClickable = false

        tvTitle.run {
            text = titleText
            textSize = titleTextSize
            setTextColor(titleTextColor)
        }

        //有文字就显示文字，否则才显示图片
        if (leftButtonText.isNotBlank() || rightButtonText.isNotBlank()) {
            if (leftButtonText.isNotBlank() && leftButtonText != "null") {
                tvLeft.visibility = View.VISIBLE
                tvLeft.text = leftButtonText
                leftLayout.isClickable = true
            }
            if (rightButtonText.isNotBlank() && rightButtonText != "null") {
                tvRight.visibility = View.VISIBLE
                tvRight.text = rightButtonText
                rightLayout.isClickable = true
            }
        } else {
            ivLeft.background = resources.getDrawable(leftButtonBackground, null)
            ivRight.background = resources.getDrawable(rightButtonBackground, null)
        }

        leftLayout.setOnClickListener {
            leftClickListener.click()
        }

        rightLayout.setOnClickListener {
            rightClickListener.click()
        }

        addView(view)
    }
}