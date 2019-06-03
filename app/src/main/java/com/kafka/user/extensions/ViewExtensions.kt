package com.kafka.user.extensions

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator

/**
 * @author Vipul Kumar; dated 27/01/19.
 */

fun View.getColor(colorRes: Int) = ContextCompat.getColor(context, colorRes)

fun View.animScaleAlphaIn(duration: Long): ViewPropertyAnimator? {
    scaleX = 1.2f
    scaleY = 1.2f
    return animate().translationY(0f)
        .scaleX(1f)
        .scaleY(1f)
        .alpha(1f).setDuration(duration)
}

fun View.animScaleAlphaOut(duration: Long): ViewPropertyAnimator? {
    scaleX = 1f
    scaleY = 1f
    return animate().translationY(-50f)
        .scaleX(1.2f)
        .scaleY(1.2f)
        .alpha(0f).setDuration(duration)
}

fun View.animateScaleOnFocus(hasFocus: Boolean, scale: Float = 1.2f) {
    if (hasFocus) {
        this.animate().scaleX(scale).scaleY(scale)
            .setInterpolator(EasingInterpolator(Ease.BACK_OUT)).duration = 420
    } else {
        this.animate().scaleX(1f).scaleY(1f)
            .setInterpolator(EasingInterpolator(Ease.BACK_OUT)).duration = 420
    }
}
