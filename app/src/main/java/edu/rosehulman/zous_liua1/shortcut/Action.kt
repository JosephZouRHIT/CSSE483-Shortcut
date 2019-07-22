package edu.rosehulman.zous_liua1.shortcut

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(var time: Int = 0, var type: ACTION_TYPE = ACTION_TYPE.TAP): Parcelable{
    //TODO: Add MotionEvent after core implementation plan confirmed
    enum class ACTION_TYPE{
        TAP,
        SWIPE
    }
}