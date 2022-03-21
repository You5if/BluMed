package com.component.pharma.ui

import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.component.pharma.R

fun View.slideRight(animTime: Long, startOffset: Long) {
    val slideRight = AnimationUtils.loadAnimation(context, R.anim.slide_right).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideRight)
}

fun View.slideLeft(animTime: Long, startOffset: Long) {
    val slideLeft = AnimationUtils.loadAnimation(context, R.anim.slide_left).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideLeft)
}