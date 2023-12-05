package com.janidu.creditcardwatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class CheckCardType(number: EditText) {
    init {
        number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val enteredCardNumber = s.toString()
                val cardType = getCardType(enteredCardNumber)
                // Do something with the card type, such as updating UI
                // For example:
                // textView.text = "Card Type: $cardType"
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed for this example
            }
        })
    }
    fun getCardType(cardNumber: String): String {
        val visaRegex = "^4[0-9]{12}(?:[0-9]{3})?\$".toRegex()
        val mastercardRegex = "^5[1-5][0-9]{14}\$".toRegex()
        val amexRegex = "^3[47][0-9]{13}\$".toRegex()
        val discoverRegex = "^6(?:011|5[0-9]{2})[0-9]{12}\$".toRegex()

        return when {
            cardNumber.matches(visaRegex) -> "Visa"
            cardNumber.matches(mastercardRegex) -> "MasterCard"
            cardNumber.matches(amexRegex) -> "American Express"
            cardNumber.matches(discoverRegex) -> "Discover"
            else -> "Unknown"
        }
    }
}