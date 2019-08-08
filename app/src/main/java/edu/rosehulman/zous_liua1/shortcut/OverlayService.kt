package edu.rosehulman.zous_liua1.shortcut

import android.app.Service
import android.content.Intent
import android.os.IBinder


class OverlayService : Service() {

    private val overlay by lazy { Overlay(this) }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        overlay.addOverlayService()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        overlay.removeOverlayService()
        super.onDestroy()
    }

}