package edu.rosehulman.zous_liua1.shortcut

import android.content.pm.PackageInfo
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class App(var name: String = "", var pkgName: String = "") : Parcelable{
//    companion object{
//        fun fromSnapShot(map: HashMap<String, String>): App{
//            return App(map.getValue(Constants.APP_NAME), map.getValue(Constants.APP_PKG))
//        }
//    }
}