package edu.rosehulman.zous_liua1.shortcut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class App(var name: String = "", var imgResource: Int = 0) : Parcelable