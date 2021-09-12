package com.riyazuddin.noteit.presentation.utill

import androidx.compose.ui.graphics.Color

object HexToJetpackColor {
    fun getColor(color: String): Color{
        return Color(android.graphics.Color.parseColor("#$color"))
    }
}