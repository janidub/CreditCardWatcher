package com.janidu.cardtextwatcher

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText


class CardCVCEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyle), TextWatcher {

    private var number: String = ""
    private val CARD_CVC_TOTAL_SYMBOLS = 3

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        number = s.toString()
    }

    override fun afterTextChanged(s: Editable) {
        onCardCVCTextChanged(s)
        number = s.toString()
    }

    protected fun onCardCVCTextChanged(s: Editable) {
        if (s.length > CARD_CVC_TOTAL_SYMBOLS) {
            s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length)
        }
    }

}