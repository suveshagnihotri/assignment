package com.suvesh.utils


import android.content.Context
import android.graphics.Typeface
import android.util.SparseArray
import java.lang.Exception

/**
 * Created by Suvesh on 08/08/2017 AD.
 */
object FontCache {

    enum class Font {
        LIGHT, NORMAL, MEDIUM, SEMIBOLD, BOLD
    }

    private val fontCache = SparseArray<Typeface>()
    @JvmStatic
    fun getTypeface(font: Int, context: Context): Typeface? {
        var typeface: Typeface? = fontCache[font]

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, "fonts/${getFontName(font)}")
            } catch (e: Exception) {
                return null
            }
            fontCache.put(font, typeface)
        }
        return typeface
    }

    private fun getFontName(font: Int) : String {
        when (font) {
            0 -> return "light.otf"
            1 -> return "normal.otf"
            2 -> return "medium.otf"
            3 -> return "semibold.otf"
            4 -> return "bold.otf"
            else -> return "normal.otf"
        }
    }
}
