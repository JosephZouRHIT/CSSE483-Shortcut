package edu.rosehulman.zous_liua1.shortcut

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShortCut(var title: String = "", var description: String = "", var appList: ArrayList<App> = ArrayList(), var isLocked: Boolean = false): Parcelable{

    @get:Exclude var id: String = ""
    companion object{
        fun fromSnapShot(doc: DocumentSnapshot) : ShortCut{
            val shortcut = doc.toObject(ShortCut::class.java)!!
            shortcut.id = doc.id
            return shortcut
        }
    }
}