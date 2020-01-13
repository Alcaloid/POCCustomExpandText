package com.poc.custom.expandtext.ui

import android.content.Context
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
    }

    private var maxLine = 3
    private var originalText: CharSequence? = null


    private fun init() {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.custom_text_expand_view, this)
        ellipsizeText.visibility = View.VISIBLE
        handleClickElipsize()
    }

    fun setExpandableText(text: CharSequence?) {
        originalText = text
        mainText.text = text

        this.post {
            updateText()
        }
    }

    fun checkVisibilityElipsizeText() {
        if (mainText.lineCount > maxLine && isExpand) {
            ellipsizeText.setText("See more")
            ellipsizeText.visibility = View.VISIBLE
        } else {
            ellipsizeText.visibility = View.VISIBLE
            ellipsizeText.setText("...See less")
        }
    }

    fun updateText() {
        if (isExpand) {
            checkVisibilityElipsizeText()
            mainText.maxLines = maxLine
        } else {
            checkVisibilityElipsizeText()
            mainText.maxLines = Int.MAX_VALUE
        }
    }

    fun handleClickElipsize() {
        ellipsizeText.setOnClickListener {
            isExpand = !isExpand
        }
    }


    private val DEFAULT_IS_EXPAND = true

    var onStateChangeListener: ((oldState2: Boolean, newState2: Boolean) -> Unit)? = null

    var isExpand = DEFAULT_IS_EXPAND
        set(value) {
            if (field != value) {
                onStateChangeListener?.let { it(field, value) }
                field = value
            }
            updateText()
        }

}
