package com.janidu.creditcardwatcher

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MaskedCardNumberTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    fun setCardNumber(cardNumber: String) {
        val trimmedCardNumber = cardNumber.replace(" ", "")
        val maskedNumber = maskCardNumber(trimmedCardNumber)


        text = maskedNumber
    }

    private fun maskCardNumber(cardNumber: String): String {
        val visibleDigits = 3
        val maskedLength = cardNumber.length - 6

        val visiblePart = cardNumber.substring(0, visibleDigits)
        val maskedPart = "X".repeat(maskedLength)
        val lastVisiblePart = cardNumber.substring(cardNumber.length - 3)

        //return "$visiblePart$maskedPart$lastVisiblePart"
        val newNum =  "$visiblePart$maskedPart$lastVisiblePart"

        val result = StringBuilder()
        var counter = 0

        for (char in newNum) {
            if (counter > 0 && counter % 4 == 0) {
                result.append(" ")
            }
            result.append(char)
            counter++
        }
        return result.toString()
    }
}