package edu.rosehulman.zous_liua1.shortcut

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shortcut_card.view.*

class ShortCutVH(itemView: View): RecyclerView.ViewHolder(itemView){

    private val title = itemView.shortcut_title
    private val description = itemView.shortcut_description
    private val button = itemView.detail_button
    private lateinit var shortcut: ShortCut

    init{
        button.setOnClickListener {
            //TODO: Add Onclick Listener
        }
    }

    fun bind(shortcut: ShortCut){
        title.text = shortcut.title
        description.text = shortcut.description
        this.shortcut = shortcut
    }
}