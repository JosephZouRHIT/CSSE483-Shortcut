package edu.rosehulman.zous_liua1.shortcut

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast

class OverlayService : Service(), View.OnTouchListener, View.OnClickListener {

    private lateinit var params: WindowManager.LayoutParams
    private lateinit var overlayButton: ImageButton
    private lateinit var windowManager: WindowManager
    private var initX = 0
    private var initY = 0
    private var firstTouchX = 0.0F
    private var firstTouchY = 0.0F
    private var isMoving = false

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        overlayButton = ImageButton(this)

        overlayButton.setImageResource(R.mipmap.ic_launcher)

        overlayButton.setOnTouchListener(this)

        overlayButton.setOnClickListener(this)

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 100

        windowManager.addView(overlayButton, params)

    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayButton)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }



    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        view!!.performClick()

        when (event!!.action){
            MotionEvent.ACTION_DOWN ->{
                initX = params.x
                initY = params.y
                firstTouchX = event.rawX
                firstTouchY = event.rawY
                isMoving = true
            }
            MotionEvent.ACTION_UP ->{
                isMoving = false
            }
            MotionEvent.ACTION_MOVE ->{
                params.x = initX + (event.rawX - firstTouchX).toInt()
                params.y = initY + (event.rawY - firstTouchY).toInt()
                windowManager.updateViewLayout(overlayButton, params)
            }
        }
        return true
    }

    override fun onClick(p0: View?) {
        //TODO:On OverlayButtonClicked method below
        if (!isMoving) Toast.makeText(this, "Button pressed", Toast.LENGTH_SHORT).show()
    }

}