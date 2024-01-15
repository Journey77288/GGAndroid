package io.ganguo.demo.util.anim

import android.view.View

import androidx.viewpager.widget.ViewPager

/**
 * Created by leo on 16/5/7.
 */
class ZoomOutPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height

        if (position < -1) { // [-Infinity,-1)
            view.alpha = 0f
            view.scaleX = defaultScale
            view.scaleY = defaultScale
        } else if (position <= 1) { // [-1,1]
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                view.translationX = horzMargin - vertMargin / 2
            } else {
                view.translationX = -horzMargin + vertMargin / 2
            }

            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            //             Fade the page relative to its size.
            view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

        } else { // (1,+Infinity]
            view.alpha = 0f
            view.scaleX = defaultScale
            view.scaleY = defaultScale
        }
    }

    companion object {

        private val MIN_SCALE = 0.9f
        private val MIN_ALPHA = 0.5f
        private val defaultScale = 0.9f
    }

}
