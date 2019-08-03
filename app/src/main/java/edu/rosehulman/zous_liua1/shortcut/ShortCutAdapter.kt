package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShortCutAdapter(var context: Context, var listener: ShortcutList.OnShortCutSelectedListener) :
    RecyclerView.Adapter<ShortCutVH>() {

    //    private var shortCutList = ArrayList<ShortCut>()
    //model object
    private var shortCutList = ArrayList<ShortCut>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortCutVH {
        val view = LayoutInflater.from(context).inflate(R.layout.shortcut_card, parent, false)
        return ShortCutVH(view, this)
    }

    override fun getItemCount(): Int = this.shortCutList.size

    override fun onBindViewHolder(holder: ShortCutVH, position: Int) {
        holder.bind(this.shortCutList[position])
    }

    fun selectShortcutAt(adapterPosition: Int) {
        val shortCut = this.shortCutList[adapterPosition]
        listener.onSCSelected(shortCut, adapterPosition)
    }

    fun selectShortcutForServiceAt(adapterPosition: Int) {
        val shortCut = this.shortCutList[adapterPosition]
        listener.onSCSelectedForService(shortCut)
    }

    fun addShortcut(shortcut: ShortCut){
        shortCutList.add(0, shortcut)
        notifyItemInserted(0)
    }

    fun editShortcut(shortcut: ShortCut, position: Int){
        shortCutList[position] = shortcut
        notifyItemChanged(position)
    }
}