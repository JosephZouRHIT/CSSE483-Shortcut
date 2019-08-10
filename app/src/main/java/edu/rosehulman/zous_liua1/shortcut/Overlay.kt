package edu.rosehulman.zous_liua1.shortcut

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_assistive_touch.view.*

class Overlay(
        private val context: Context
, private val shortcut: ShortCut) : View.OnClickListener {

    private val overlayLocal = LayoutInflater.from(context).inflate(R.layout.layout_assistive_touch, null)
    private val overlayWindowManager by lazy {
        OverlayWindowManager(context)
    }
    private val player = Player(shortcut, context)
    private val lp: WindowManager.LayoutParams by lazy {
        val temp = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getCompatWindowType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.RGBA_8888
        )
        temp.gravity = Gravity.START or Gravity.TOP
        temp
    }
    private var scrollAnimator: ObjectAnimator? = null
    private var deltaX: Float = 0F
    private var deltaY: Float = 0F
    private var windowWidth: Int = 0
    private var windowHeight: Int = 0
    private var inMotion = false

    init {
        overlayLocal.menuView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollAnimator?.cancel()
                    deltaX = event.rawX
                    deltaY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val diffX = event.rawX - deltaX
                    val diffY = event.rawY - deltaY
                    deltaX = event.rawX
                    deltaY = event.rawY
                    lp.x = Math.floor(lp.x + diffX + 0.5).toInt()
                    lp.y = Math.floor(lp.y + diffY + 0.5).toInt()
                    adjustPositionWhenMove()
                    overlayWindowManager.updateOverlay(overlayLocal, lp)
                    inMotion = true
                }
                MotionEvent.ACTION_UP -> adjustPositionWhenActionUp()
            }
            false
        }
        overlayLocal.menuView.setOnClickListener {
            if (!inMotion) {
                overlayLocal.menuView.visibility = View.GONE
                overlayLocal.menuCl.visibility = View.VISIBLE
            } else {
                inMotion = false
            }
        }
        overlayLocal.menuView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @SuppressLint("ObsoleteSdkInt")
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    overlayLocal.menuView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                windowWidth = overlayLocal.menuView.width
                windowHeight = overlayLocal.menuView.height
            }

        })

        overlayLocal.stop_btn.setOnClickListener(this)
        overlayLocal.prev_btn.setOnClickListener(this)
        overlayLocal.next_btn.setOnClickListener(this)
        overlayLocal.home_btn.setOnClickListener(this)
        overlayLocal.menuCl.setOnClickListener(this)
        player.startShortcut()
    }

    private fun adjustPositionWhenMove() {
        if (lp.x < 0) {
            lp.x = 0
        }
        if (lp.x + windowWidth > overlayWindowManager.windowWidth) {
            lp.x = overlayWindowManager.windowWidth - windowWidth
        }

        if (lp.y < 0) {
            lp.y = 0
        }
        if (lp.y + windowHeight > overlayWindowManager.surplusHeight) {
            lp.y = overlayWindowManager.surplusHeight - windowHeight
        }
    }

    private fun adjustPositionWhenActionUp() {
        val left = lp.x
        val top = lp.y
        val right = overlayWindowManager.windowWidth - windowWidth - left
        val bottom = overlayWindowManager.windowHeight - windowHeight - top
        scrollAnimator = ObjectAnimator()
        when (mapOf(left to 0, top to 1, right to 2, bottom to 3).minBy { it.key }?.value) {
            0 -> {
                scrollAnimator?.duration = left.toLong()
                scrollAnimator?.setIntValues(left, 0)
            }
            1 -> {
                scrollAnimator?.duration = top.toLong()
                scrollAnimator?.setIntValues(top, 0)
            }
            2 -> {
                scrollAnimator?.duration = right.toLong()
                scrollAnimator?.setIntValues(left, overlayWindowManager.windowWidth - windowWidth)
            }
            3 -> {
                scrollAnimator?.duration = bottom.toLong()
                scrollAnimator?.setIntValues(top, overlayWindowManager.windowHeight - windowHeight)
            }
        }
        scrollAnimator?.target = this
        scrollAnimator?.interpolator = LinearInterpolator()
        scrollAnimator?.start()
    }

    private fun getCompatWindowType(): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    } else {
        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.stop_btn -> {
                removeOverlayService()
                Toast.makeText(context, "Shortcut Stopped", Toast.LENGTH_SHORT).show()
            }
            R.id.prev_btn -> {
                player.previousApp()
                Toast.makeText(context, "App ${player.position}", Toast.LENGTH_SHORT).show()
            }
            R.id.next_btn -> {
                player.nextApp()
                Toast.makeText(context, "App ${player.position}", Toast.LENGTH_SHORT).show()
            }
            R.id.home_btn -> {
                player.toShortCut()
            }
        }
        overlayLocal.menuCl.visibility = View.GONE
        overlayLocal.menuView.visibility = View.VISIBLE
    }

    fun addOverlayService() {
        overlayWindowManager.addOverlay(overlayLocal, lp)
    }

    fun removeOverlayService() {
        overlayWindowManager.removeOverlay(overlayLocal)
    }

}