package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.installed_app_card.view.*

class InstalledAppVH(itemView: View, val context: Context, val adapter: InstalledAppAdapter): RecyclerView.ViewHolder(itemView) {
    private val appName = itemView.app_name_card
    private val appIcon = itemView.app_icon_card
    private var isSelected = false

    init {
        itemView.setOnClickListener {
            isSelected = !isSelected
            if(isSelected){
                itemView.setBackgroundColor(context.getColor(R.color.colorRed))
                adapter.selectApp(adapterPosition)
            }else{
                itemView.setBackgroundColor(context.getColor(R.color.colorWhite))
                adapter.deselectApp(adapterPosition)
            }
        }
    }

    fun setSelected(select: Boolean){
        isSelected = select
        if(isSelected){
            itemView.setBackgroundColor(context.getColor(R.color.colorRed))
        }else{
            itemView.setBackgroundColor(context.getColor(R.color.colorWhite))
        }
    }

    fun bind(app: App){
        appName.text = app.name
        appIcon.setImageDrawable((context as MainActivity).packageManager.getApplicationIcon(app.pkgName))
    }

}