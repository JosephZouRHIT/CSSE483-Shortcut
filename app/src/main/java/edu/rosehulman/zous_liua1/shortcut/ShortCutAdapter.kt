package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShortCutAdapter(var context: Context): RecyclerView.Adapter<ShortCutVH>(){

    private val shortcutList = ArrayList<ShortCut>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortCutVH {
        val view = LayoutInflater.from(context).inflate(R.layout.shortcut_card, parent, false)
        return ShortCutVH(view)
    }

    override fun getItemCount(): Int = shortcutList.size

    override fun onBindViewHolder(holder: ShortCutVH, position: Int) {
        holder.bind(shortcutList[position])
    }
}