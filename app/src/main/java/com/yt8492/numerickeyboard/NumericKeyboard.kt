package com.yt8492.numerickeyboard

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.children
import kotlinx.android.synthetic.main.numeric_keyboard.view.*

class NumericKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TableLayout(context, attrs), View.OnClickListener {
    private val keyValues = SparseArray<String>().apply {
        put(R.id.numeric_key_0, "0")
        put(R.id.numeric_key_1, "1")
        put(R.id.numeric_key_2, "2")
        put(R.id.numeric_key_3, "3")
        put(R.id.numeric_key_4, "4")
        put(R.id.numeric_key_5, "5")
        put(R.id.numeric_key_6, "6")
        put(R.id.numeric_key_7, "7")
        put(R.id.numeric_key_8, "8")
        put(R.id.numeric_key_9, "9")
    }
    private val targetFieldId: Int
    private lateinit var inputConnection: InputConnection
    init {
        context.obtainStyledAttributes(attrs, R.styleable.NumericKeyboard).also { typedArray ->
            targetFieldId = typedArray.getResourceIdOrThrow(R.styleable.NumericKeyboard_targetField)
        }.recycle()
        LayoutInflater.from(context).inflate(R.layout.numeric_keyboard, this, true).apply {
            numeric_key_0.setOnClickListener(this@NumericKeyboard)
            numeric_key_1.setOnClickListener(this@NumericKeyboard)
            numeric_key_2.setOnClickListener(this@NumericKeyboard)
            numeric_key_3.setOnClickListener(this@NumericKeyboard)
            numeric_key_4.setOnClickListener(this@NumericKeyboard)
            numeric_key_5.setOnClickListener(this@NumericKeyboard)
            numeric_key_6.setOnClickListener(this@NumericKeyboard)
            numeric_key_7.setOnClickListener(this@NumericKeyboard)
            numeric_key_8.setOnClickListener(this@NumericKeyboard)
            numeric_key_9.setOnClickListener(this@NumericKeyboard)
            numeric_key_backspace.setOnClickListener(this@NumericKeyboard)
        }
        dividerDrawable = context.getDrawable(R.drawable.keyboard_divider)
        showDividers = SHOW_DIVIDER_BEGINNING or LinearLayout.SHOW_DIVIDER_MIDDLE
        children.mapNotNull { it as? TableRow }
            .forEach { it.dividerDrawable = dividerDrawable }
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
            val selectedText: CharSequence? = inputConnection.getSelectedText(0)
            if (selectedText.isNullOrEmpty()) {
                inputConnection.deleteSurroundingText(1, 0)
            }
        } else {
            val value = keyValues[v.id]
            inputConnection.commitText(value, 1)
        }
    }
}