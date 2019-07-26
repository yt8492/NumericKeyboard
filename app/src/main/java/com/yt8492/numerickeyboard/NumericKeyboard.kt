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
            keyValues.keys.forEach { id ->
                findViewById<View>(id).setOnClickListener(this@NumericKeyboard)
            }
            findViewById<View>(R.id.numeric_key_backspace).setOnClickListener(this@NumericKeyboard)
        }
        dividerDrawable = context.getDrawable(R.drawable.keyboard_divider)
        showDividers = LinearLayout.SHOW_DIVIDER_BEGINNING or LinearLayout.SHOW_DIVIDER_MIDDLE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val targetField: TextView = (parent as View).findViewById(targetFieldId)
        targetField.isFocusable = false
        inputConnection = targetField.onCreateInputConnection(EditorInfo())
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        if (view.id == R.id.numeric_key_backspace) {
            inputConnection.deleteSurroundingText(1, 0)
        } else if (view.id in keyValues.keys) {
            val value = keyValues[view.id] ?: return
            inputConnection.commitText(value, 1)
        }
    }
}