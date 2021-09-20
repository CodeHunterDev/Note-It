package com.riyazuddin.noteit.common

import androidx.compose.ui.graphics.Color
import java.lang.NumberFormatException

object HexToJetpackColor {
    fun getColor(color: String): Color{
        return try {
            Color(android.graphics.Color.parseColor("#$color"))
        }catch (e: NumberFormatException){
            Color(android.graphics.Color.parseColor("#FF0036"))
        }
    }
}