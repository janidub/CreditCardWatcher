package com.janidu.cardtextwatcher

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText


class CardNumberEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyle), TextWatcher {

    private var number: String = ""
    private val TOTAL_SYMBOLS = 19 // size of pattern 0000-0000-0000-0000
    private val TOTAL_DIGITS = 16 // max numbers of digits in pattern: 0000 x 4
    private val DIVIDER_MODULO = 5 // means divider position is every 5th symbol beginning with 1
    private val DIVIDER_POSITION =
        DIVIDER_MODULO - 1 // means divider position is every 4th symbol beginning with 0
    private val DIVIDER = ' '

    private val CARD_DATE_TOTAL_SYMBOLS = 5 // size of pattern MM/YY
    private val CARD_DATE_TOTAL_DIGITS = 4 // max numbers of digits in pattern: MM + YY
    private val CARD_DATE_DIVIDER_MODULO =
        3 // means divider position is every 3rd symbol beginning with 1
    private val CARD_DATE_DIVIDER_POSITION =
        CARD_DATE_DIVIDER_MODULO - 1 // means divider position is every 2nd symbol beginning with 0
    private val CARD_DATE_DIVIDER = '/'

    private val CARD_CVC_TOTAL_SYMBOLS = 3

    //init {
    //    addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        number = s.toString()
    }

    override fun afterTextChanged(s: Editable) {
        // if (type == Constant.CARD_NUMBER) {
        onCardNumberTextChanged(s)
        number = s.toString()
        //checkCardType()
        //}
        /*if (type == Constant.CARD_EXP_DATE) {
    onCardDateTextChanged(s)
}
if (type == Constant.CARD_CVC) {
    onCardCVCTextChanged(s)

}*/
    }
    //     })
    // }


    private fun buildCorrectString(
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

    private fun onCardNumberTextChanged(s: Editable) {
        if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
            s.replace(
                0,
                s.length,
                buildCorrectString(
                    getDigitArray(s, TOTAL_DIGITS),
                    DIVIDER_POSITION,
                    DIVIDER
                )
            );
        }
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

    protected fun onCardCVCTextChanged(s: Editable) {
        if (s.length > CARD_CVC_TOTAL_SYMBOLS) {
            s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length)
        }
    }

    fun checkCardType(): String? {
//val imageView: ImageView = findViewById(R.id.iv_card)
        val trimmedCardNumber = number.replace(" ", "")
        val visaPattern = Regex("^4[0-9]{0,}\$")
        val mastercardPattern = Regex("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{0,}\$")
        val amexPattern = Regex("^3[47][0-9]{0,}\$")
        return when {
            visaPattern.matches(trimmedCardNumber) -> {
                Log.e("visa", trimmedCardNumber)
                Constants.VISA
            }

            mastercardPattern.matches(trimmedCardNumber) -> {
                Log.e("master", trimmedCardNumber)
                Constants.MASTERCARD
            }

            amexPattern.matches(trimmedCardNumber) -> {
                Log.e("amex", trimmedCardNumber)
                Constants.AMEX
            }

            else -> {
                Log.e("null", trimmedCardNumber)
                Constants.EMPTY
            }
        }
    }

}