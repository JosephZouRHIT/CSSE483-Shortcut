package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(var context: Context,var position: Int, var shortcut: ShortCut, var listener: ShortCutEdit.OnAppClickedListener?, val isEditable: Boolean): RecyclerView.Adapter<AppVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVH {
        val view = LayoutInflater.from(context).inflate(R.layout.app_card, parent, false)
        return AppVH(view, context, this)
    }

    override fun getItemCount(): Int = shortcut.appList.size

    override fun onBindViewHolder(holder: AppVH, position: Int) {
        holder.bind(shortcut.appList[position])
    }

    fun appClicked(){
        if(isEditable){
            listener?.onAppClicked(shortcut, position)
        }
    }
}