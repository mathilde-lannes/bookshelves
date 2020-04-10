package com.maty.android.bookshelves.common

import android.graphics.Bitmap
import android.graphics.Color
import androidx.palette.graphics.Palette


fun getDominantColor(bitmap: Bitmap): Int {
    val swatch : Palette.Swatch? = Palette.from(bitmap).generate().dominantSwatch
    return swatch?.rgb ?: Color.parseColor("#377375")
}