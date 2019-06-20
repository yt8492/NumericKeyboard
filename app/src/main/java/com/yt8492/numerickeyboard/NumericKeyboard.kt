package com.yt8492.numerickeyboard

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
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
    private val keyValues = SparseArray<String>().apply {
        put(R.id.numericKey0, "0")
        put(R.id.numericKey1, "1")
        put(R.id.numericKey2, "2")
        put(R.id.numericKey3, "3")
        put(R.id.numericKey4, "4")
        put(R.id.numericKey5, "5")
        put(R.id.numericKey6, "6")
        put(R.id.numericKey7, "7")
        put(R.id.numericKey8, "8")
        put(R.id.numericKey9, "9")
    }
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
        } else {
            val value = keyValues[v.id]
            inputConnection.commitText(value, 1)
        }
    }
}