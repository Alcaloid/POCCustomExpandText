package com.poc.custom.expandtext.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.poc.custom.expandtext.R

class CustomExpandText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    companion object {
        private const val DEFAULT_IS_EXPAND = false
        private const val DEFAULT_NEED_EXPAND_ONE_TIME = false
        private const val DEFAULT_MAX_LINE = 3
        private const val DEFAULT_EXPAND_TEXT = "...See More"
        private const val DEFAULT_EXPAND_Color = Color.BLUE
    }

    var onStateChangeListener: (CustomExpandText.(oldState2: Boolean, newState2: Boolean) -> Unit)? =
        null

    var isExpand = DEFAULT_IS_EXPAND
        set(value) {
            if (field != value) {
                onStateChangeListener?.let { it(field, value) }
                field = value
            }
            updateText()
        }
    private var expandMaxLine: Int = DEFAULT_MAX_LINE
    private var expandMoreText: String = DEFAULT_EXPAND_TEXT
    private var expandMoreColor: Int = DEFAULT_EXPAND_Color
    private var needExpandOneTime: Boolean = DEFAULT_NEED_EXPAND_ONE_TIME
    private var expandOneTime: Boolean = false
    private var originalText: CharSequence? = null

    init {
        initAttribute(context, attrs)
        setOnClickListener {
            if (!expandOneTime) {
                isExpand = !isExpand
                updateText()
            }
        }
    }

    fun setMaxLine(limitLine: Int) {
        expandMaxLine = limitLine
    }

    fun setExpandOneTime(state: Boolean) {
        needExpandOneTime = state
    }

    fun setExpandMoreText(text: String) {
        expandMoreText = text
    }

    fun setExpandMoreColor(color: Int) {
        expandMoreColor = color
    }

    fun setExpandableText(text: CharSequence?) {
        originalText = text
        this.post {
            updateText()
        }
    }

    private fun updateText() {
        if (isExpand) {
            if (needExpandOneTime) {
                expandOneTime = true
            }
            maxLines = Int.MAX_VALUE //set to control when user scroll
            makeTextViewResizable(Int.MAX_VALUE)
        } else {
            maxLines = expandMaxLine //set to control when user scroll
            makeTextViewResizable(expandMaxLine)
        }
    }

    private fun makeTextViewResizable(maxLine: Int) {
        val textView = this
        when {
            textView.lineCount >= maxLine -> {
                val expandText = expandMoreText
                val lineEndIndex = textView.layout.getLineEnd(maxLine - 1)
                val wordDefault = if (!isEnoughWordLastLine(lineEndIndex, expandText.length)) {
                    textView.text.subSequence(0, lineEndIndex).toString().trim()
                } else {
                    textView.text.subSequence(
                        0,
                        lineEndIndex - expandText.length
                    ).toString().trim()
                }
                val wordMore = SpannableString(expandText).also {
                    it.setSpan(
                        ForegroundColorSpan(expandMoreColor),
                        0,
                        it.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                textView.text = wordDefault
                textView.append(wordMore)
            }
            else -> {
                textView.text = originalText
            }
        }
    }

    @SuppressLint("Recycle")
    private fun initAttribute(context: Context, attrs: AttributeSet?) {
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
            ) ?: DEFAULT_EXPAND_TEXT
            expandMoreColor = it.getInt(
                R.styleable.ExpandableTextView_expandableColorText,
                DEFAULT_EXPAND_Color
            )
            it.recycle()
        }
    }

    private fun isEnoughWordLastLine(startLength: Int, expandTextLength: Int): Boolean {
        return if (isEnoughSpace(startLength, expandTextLength)) {
            isEnoughCharacter(startLength, expandTextLength)
        } else {
            false
        }
    }

    private fun isEnoughSpace(startLength: Int, expandTextLength: Int): Boolean {
        return startLength > expandTextLength
    }

    private fun isEnoughCharacter(startLength: Int, expandTextLength: Int): Boolean {
        val target = text.subSequence(startLength - expandTextLength, startLength)
        target.forEach {
            if (it == '\n') {
                return false
            }
        }
        return true
    }
}