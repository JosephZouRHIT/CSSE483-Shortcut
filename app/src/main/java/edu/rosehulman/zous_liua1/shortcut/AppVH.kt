package edu.rosehulman.zous_liua1.shortcut

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.app_card.view.*

class AppVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val imgView = itemView.app_icon_card
    private lateinit var app: App

    init{
        imgView.setOnClickListener {

        }
    }

    fun bind(app: App){
        //TODO: change after finding ways to grab app icons
        imgView.setImageResource(app.imgResource)
        this.app = app
    }
}