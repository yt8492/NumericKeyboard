package com.yt8492.numerickeyboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.content.res.getResourceIdOrThrow
import kotlinx.android.synthetic.main.numeric_keyboard.view.*

class NumericKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TableLayout(context, attrs), View.OnClickListener {
    private val keyValues = mapOf(
        R.id.numericKey0 to "0",
        R.id.numericKey1 to "1",
        R.id.numericKey2 to "2",
        R.id.numericKey3 to "3",
        R.id.numericKey4 to "4",
        R.id.numericKey5 to "5",
        R.id.numericKey6 to "6",
        R.id.numericKey7 to "7",
        R.id.numericKey8 to "8",
        R.id.numericKey9 to "9"
    )
    private val targetFieldId: Int
    private lateinit var inputConnection: InputConnection
    init {
        context.obtainStyledAttributes(attrs, R.styleable.NumericKeyboard).also { typedArray ->
            targetFieldId = typedArray.getResourceIdOrThrow(R.styleable.NumericKeyboard_targetField)
        }.recycle()
        View.inflate(context, R.layout.numeric_keyboard, this).apply {
            numericKey0.setOnClickListener(this@NumericKeyboard)
            numericKey1.setOnClickListener(this@NumericKeyboard)
            numericKey2.setOnClickListener(this@NumericKeyboard)
            numericKey3.setOnClickListener(this@NumericKeyboard)
            numericKey4.setOnClickListener(this@NumericKeyboard)
            numericKey5.setOnClickListener(this@NumericKeyboard)
            numericKey6.setOnClickListener(this@NumericKeyboard)
            numericKey7.setOnClickListener(this@NumericKeyboard)
            numericKey8.setOnClickListener(this@NumericKeyboard)
            numericKey9.setOnClickListener(this@NumericKeyboard)
            numeric_key_backspace.setOnClickListener(this@NumericKeyboard)
        }
        dividerDrawable = context.getDrawable(R.drawable.keyboard_divider)
        showDividers = SHOW_DIVIDER_BEGINNING or LinearLayout.SHOW_DIVIDER_MIDDLE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val targetField: TextView = (parent as View).findViewById(targetFieldId)
        targetField.isFocusable = false
        inputConnection = targetField.onCreateInputConnection(EditorInfo())
    }

    override fun onClick(v: View?) {
        v ?: return
        if (v.id == R.id.numeric_key_backspace) {
            inputConnection.deleteSurroundingText(1, 0)
        } else if (v.id in keyValues.keys) {
            val value = keyValues[v.id] ?: return
            inputConnection.commitText(value, 1)
        }
    }
}