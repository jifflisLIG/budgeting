package com.sakayta.budgetapp.util

import java.text.DecimalFormat

class Numb {

    companion object{

        fun formatDecimalWithComma(value:Double):String{
            return DecimalFormat("#,###.##").format(value)
        }
    }

}