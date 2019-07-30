package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.app_card.view.*

class AppVH(itemView: View, val context:Context, val adapter: AppAdapter): RecyclerView.ViewHolder(itemView) {
    private val imgView = itemView.app_icon_card
    private lateinit var app: App

    init{
        imgView.setOnClickListener {
            adapter.appClicked(adapterPosition)
        }
    }

    fun bind(app: App){
        imgView.setImageDrawable(app.pkgInfo.applicationInfo.loadIcon(context.packageManager))
        this.app = app
    }
}