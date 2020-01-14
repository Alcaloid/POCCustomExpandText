package com.poc.custom.expandtext.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.poc.custom.expandtext.R
import kotlinx.android.synthetic.main.custom_text_expand_view.view.ellipsizeText
import kotlinx.android.synthetic.main.custom_text_expand_view.view.mainText


class CustomTextButtonExpand : LinearLayoutCompat {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    companion object {
        private const val DEFAULT_IS_EXPAND = true
        private const val DEFAULT_NEED_EXPAND_ONE_TIME = false
        private const val DEFAULT_MAX_LINE = 5
        private const val DEFAULT_EXPAND_MORE_TEXT = "...See More"
        private const val DEFAULT_EXPAND_LESS_TEXT = "See Less"
        private const val DEFAULT_EXPAND_Color = Color.BLUE
    }

    var onStateChangeListener: ((oldState2: Boolean, newState2: Boolean) -> Unit)? = null

    var isExpand = DEFAULT_IS_EXPAND
        set(value) {
            if (field != value) {
                onStateChangeListener?.let { it(field, value) }
                field = value
            }
            updateText()
        }
    private var expandMaxLine: Int = DEFAULT_MAX_LINE
    private var expandMoreText: String = DEFAULT_EXPAND_MORE_TEXT
    private var expandLessText: String = DEFAULT_EXPAND_LESS_TEXT
    private var expandMoreColor: Int = DEFAULT_EXPAND_Color
    private var needExpandOneTime: Boolean = DEFAULT_NEED_EXPAND_ONE_TIME
    private var expandOneTime: Boolean = false
    private var originalText: CharSequence? = null


    private fun init() {
//        initDataSetting(context, attrs)
        initCustomView()
    }

    private fun initCustomView() {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.custom_text_expand_view, this)
        ellipsizeText.setTextColor(expandMoreColor)
        ellipsizeText.visibility = View.VISIBLE
        handleClickEllipsize()
    }

    @SuppressLint("Recycle")
    private fun initDataSetting(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView).also {
            expandMaxLine =
                it.getInt(R.styleable.ExpandableTextView_expandableMaxLine, DEFAULT_MAX_LINE)
            isExpand =
                it.getBoolean(R.styleable.ExpandableTextView_expandableIsExpand, DEFAULT_IS_EXPAND)
            needExpandOneTime =
                it.getBoolean(
                    R.styleable.ExpandableTextView_expandableCanExpand,
                    DEFAULT_NEED_EXPAND_ONE_TIME
                )
            expandMoreText = it.getString(
                R.styleable.ExpandableTextView_expandableText
            ) ?: DEFAULT_EXPAND_MORE_TEXT
            expandMoreColor = it.getInt(
                R.styleable.ExpandableTextView_expandableColorText,
                DEFAULT_EXPAND_Color
            )
            it.recycle()
        }
    }

    fun setMaxLine(limitLine: Int) {
        expandMaxLine = limitLine
    }

    fun setExpandOneTime(state: Boolean) {
        needExpandOneTime = state
    }

    fun setExpandText(
        txtExpandMore: String = DEFAULT_EXPAND_MORE_TEXT,
        txtExpandLess: String = DEFAULT_EXPAND_LESS_TEXT
    ) {
        expandMoreText = txtExpandMore
        expandLessText = txtExpandLess
    }

    fun setExpandMoreColor(color: Int) {
        expandMoreColor = color
    }

    fun setExpandableText(text: CharSequence?) {
        originalText = text
        mainText.text = text

        this.post {
            updateText()
        }
    }

    private fun checkVisibilityEllipsizeText() {
        if (mainText.lineCount > expandMaxLine && isExpand && !expandOneTime) {
            ellipsizeText.text = expandMoreText
            ellipsizeText.visibility = View.VISIBLE
        } else if (!isExpand && !expandOneTime) {
            ellipsizeText.visibility = View.VISIBLE
            ellipsizeText.text = expandLessText
        } else {
            ellipsizeText.visibility = View.GONE
        }
    }

    private fun updateText() {
        if (isExpand && !expandOneTime) {
            checkVisibilityEllipsizeText()
            mainText.maxLines = expandMaxLine
        } else {
            if (needExpandOneTime) {
                expandOneTime = true
                ellipsizeText.visibility = View.GONE
            }
            checkVisibilityEllipsizeText()
            mainText.maxLines = Int.MAX_VALUE
        }
    }

    private fun handleClickEllipsize() {
        ellipsizeText.setOnClickListener {
            isExpand = !isExpand
        }
    }
}
