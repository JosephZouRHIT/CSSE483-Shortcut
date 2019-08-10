package edu.rosehulman.zous_liua1.shortcut

import android.app.Service
import android.content.Intent
import android.os.IBinder


class OverlayService : Service() {
    private lateinit var shortCut: ShortCut

    private lateinit var overlay: Lazy<Overlay>
//    private val overlay by lazy { Overlay(this) }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        shortCut = intent!!.getParcelableExtra(Constants.DEFAULT_SHORTCUT_NAME)
        overlay = lazy { Overlay(this, shortCut) }
        overlay.value.addOverlayService()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        overlay.value.removeOverlayService()
        super.onDestroy()
    }

}