package com.dexter.kotlinbaseapp.utils

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView

/**
 * Created by Admin on 06-12-2017.
 */

object TextUtils {

    fun setString(textView: TextView, s: String) {
        if (!android.text.TextUtils.isEmpty(s)) {
            textView.text = s
        }
    }

    fun getCapitalizedString(s: String): String {
        return if (!android.text.TextUtils.isEmpty(s)) {
            s.substring(0, 1).toUpperCase() + s.substring(1)
        } else {
            ""
        }
    }

    fun setSpannableString(context: Context, @ColorRes color: Int, textView: TextView, span: String, s: String) {
        var s = s
        if (!android.text.TextUtils.isEmpty(s) && !android.text.TextUtils.isEmpty(s)) {
            s = getCapitalizedString(s)
            val toSpan = span + " " + s
            val spannableString = SpannableString(toSpan)
            val spanIndex = toSpan.indexOf(span)
            spannableString.setSpan(StyleSpan(Typeface.BOLD), spanIndex, spanIndex + span.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), spanIndex, spanIndex + span.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = spannableString
        }
    }

    fun getStringFromStringResId(context: Context, @StringRes stringId: Int): String {
        return context.getString(stringId)
    }
}
