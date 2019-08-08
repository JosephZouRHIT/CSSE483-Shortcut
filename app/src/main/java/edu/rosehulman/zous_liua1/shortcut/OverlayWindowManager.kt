package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

class OverlayWindowManager(
        private val context: Context
) {

    private val wm: WindowManager? by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager?
    }
    val windowWidth: Int
    val windowHeight: Int
    val barHeight: Int
    val surplusHeight: Int

    init {
        val metrics = DisplayMetrics()
        wm?.defaultDisplay?.getMetrics(metrics)
        windowWidth = Math.floor(metrics.widthPixels.toDouble()).toInt()
        windowHeight = Math.floor(metrics.heightPixels.toDouble()).toInt()
        barHeight = calculateStatusBarHeight()
        surplusHeight = windowHeight - barHeight
    }

    fun addOverlay(Overlay: View, lp: WindowManager.LayoutParams) {
        wm?.addView(Overlay, lp)
    }

    fun updateOverlay(Overlay: View?, lp: WindowManager.LayoutParams) {
        wm?.updateViewLayout(Overlay, lp)
    }

    fun removeOverlay(Overlay: View) {
        wm?.removeView(Overlay)
    }

    private fun calculateStatusBarHeight(): Int {
        val statusBarResourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarResourceId <= 0) {
            return 0
        }
        val statusBarHeight = context.resources.getDimensionPixelSize(statusBarResourceId)
        return Math.floor(statusBarHeight.toDouble()).toInt()
    }

}