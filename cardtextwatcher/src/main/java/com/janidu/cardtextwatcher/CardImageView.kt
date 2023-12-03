package com.janidu.cardtextwatcher

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CardImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs,defStyle) {

    //private fun setImage(type:Int,)

    fun changeImage(type:String?){
        var image =0
        if (type==Constants.MASTERCARD){
            image = R.drawable.ic_master
            setImageResource(image)
        }else if(type==Constants.VISA){
            image = R.drawable.ic_visa
            setImageResource(image)
        }else if(type==Constants.DISCOVER){
            image = R.drawable.ic_discovery
            setImageResource(image)
        } else if (type==Constants.AMEX){
            image = R.drawable.ic_american_express
            setImageResource(image)
        }else
            image = R.drawable.ic_card
            setImageResource(image)

    }

}