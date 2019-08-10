package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.content.Intent
import android.widget.Toast

class Player(private var shortcut: ShortCut, private var context: Context){
    var position = 0

    fun startShortcut(){
        position = 0
        startAppAt(position)
    }

    fun nextApp(){
        position = (position + 1) % shortcut.appList.size
        startAppAt(position)
    }

    fun previousApp(){
        position--
        if(position < 0){
            position += shortcut.appList.size
        }
        startAppAt(position)
    }

    fun toShortCut(){
        val intent = (context as OverlayService).packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Cannot launch app", Toast.LENGTH_LONG).show()
        }
    }

    private fun startAppAt(pos: Int){
        val intent = (context as OverlayService).packageManager.getLaunchIntentForPackage(shortcut.appList[pos].pkgName)
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Cannot launch app", Toast.LENGTH_LONG).show()
        }
    }
}