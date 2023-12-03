package com.janidu.cardtextwatcher

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

class CardExpDateEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyle), TextWatcher {

    private var number: String = ""

    private val CARD_DATE_TOTAL_SYMBOLS = 5 // size of pattern MM/YY
    private val CARD_DATE_TOTAL_DIGITS = 4 // max numbers of digits in pattern: MM + YY
    private val CARD_DATE_DIVIDER_MODULO = 3 // means divider position is every 3rd symbol beginning with 1
    private val CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1 // means divider position is every 2nd symbol beginning with 0
    private val CARD_DATE_DIVIDER = '/'

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        number = s.toString()
    }

    override fun afterTextChanged(s: Editable) {
        onCardDateTextChanged(s)
        number = s.toString()
    }

    private fun concatString(
        digits: CharArray,
        dividerPosition: Int,
        divider: Char
    ): String? {
        val formatted = StringBuilder()
        for (i in digits.indices) {
            if (digits[i].code != 0) {
                formatted.append(digits[i])
                if (i > 0 && i < digits.size - 1 && (i + 1) % dividerPosition == 0) {
                    formatted.append(divider)
                }
            }
        }
        return formatted.toString()
    }

    private fun isInputCorrect(
        s: Editable,
        totalSymbols: Int,
        dividerModulo: Int,
        divider: Char
    ): Boolean {
        var isCorrect = s.length <= totalSymbols // check size of entered string
        for (i in 0 until s.length) { // check that every element is right
            isCorrect = if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect and (divider == s[i])
            } else {
                isCorrect and Character.isDigit(s[i])
            }
        }
        return isCorrect
    }

    private fun getDigitArray(s: Editable, size: Int): CharArray {
        val digits = CharArray(size)
        var index = 0
        var i = 0
        while (i < s.length && index < size) {
            val current = s[i]
            if (Character.isDigit(current)) {
                digits[index] = current
                index++
            }
            i++
        }
        return digits
    }


    private fun onCardDateTextChanged(s: Editable) {
        if (!isInputCorrect(
                s,
                CARD_DATE_TOTAL_SYMBOLS,
                CARD_DATE_DIVIDER_MODULO,
                CARD_DATE_DIVIDER
            )
        ) {
            s.replace(
                0,
                s.length,
                concatString(
                    getDigitArray(s, CARD_DATE_TOTAL_DIGITS),
                    CARD_DATE_DIVIDER_POSITION,
                    CARD_DATE_DIVIDER
                )
            )
        }
    }
}