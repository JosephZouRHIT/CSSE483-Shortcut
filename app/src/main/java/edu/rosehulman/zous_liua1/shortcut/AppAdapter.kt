package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(var context: Context, var appList: ArrayList<App>, var listener: ShortCutEdit.OnAppClickedListener): RecyclerView.Adapter<AppVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppVH {
        val view = LayoutInflater.from(context).inflate(R.layout.app_card, parent, false)
        return AppVH(view, context, this)
    }

    override fun getItemCount(): Int = appList.size

    override fun onBindViewHolder(holder: AppVH, position: Int) {
        holder.bind(appList[position])
    }

    fun appClicked(position: Int){
        listener.onAppClicked(appList[position])
    }
}