package com.janidu.creditcardwatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import com.janidu.cardtextwatcher.CardCVCEditText
import com.janidu.cardtextwatcher.CardExpDateEditText
import com.janidu.cardtextwatcher.CardImageView
import com.janidu.cardtextwatcher.CardNumberEditText

class AddCardActivity: AppCompatActivity(), View.OnClickListener {
    lateinit var cardNumber: CardNumberEditText
    lateinit var cardExpNumber: CardExpDateEditText
    lateinit var cardCvcNumber: CardCVCEditText
    lateinit var cardImage: CardImageView
    lateinit var number:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

        val textNumWatcher = CardNumberEditText(this)
        cardNumber.addTextChangedListener(textNumWatcher)

        val textEXPWatcher = CardExpDateEditText(this)
        cardExpNumber.addTextChangedListener(textEXPWatcher)

        val textCVCWatcher = CardExpDateEditText(this)
        cardCvcNumber.addTextChangedListener(textCVCWatcher)

        cardNumber.addTextChangedListener {
           var type =  cardNumber.checkCardType()
            number = cardNumber.text.toString()

          cardImage.changeImage(type)


        }
    }

    private fun initUI(){
        cardNumber = findViewById(R.id.et_cardNumber)
        cardExpNumber = findViewById(R.id.et_cardExpDate)
        cardCvcNumber = findViewById(R.id.et_cardCvcNumber)
        cardImage = findViewById(R.id.iv_card)
        findViewById<Button>(R.id.btn_add_card).setOnClickListener(this)
        //button.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.btn_add_card ->{
                println("numberrrrr$number")
                Log.e("number","button click")
                val intent = Intent(this,ViewAddedCards::class.java)
                intent.putExtra("CARD_NUMBER",number)
                startActivity(intent)
            }
        }
    }

}