package edu.rosehulman.zous_liua1.shortcut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShortCut(var title: String = "", var description: String = "", var appList: ArrayList<App> = ArrayList()): Parcelable