package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class InstalledAppAdapter(var context: Context, var appList: ArrayList<App>): RecyclerView.Adapter<InstalledAppVH>() {

    var selectedApps = ArrayList<App>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstalledAppVH {
        val view = LayoutInflater.from(context).inflate(R.layout.installed_app_card, parent, false)
        return InstalledAppVH(view, context, this)
    }

    override fun getItemCount(): Int = appList.size

    override fun onBindViewHolder(holder: InstalledAppVH, position: Int) {
        holder.bind(appList[position])
    }

    fun selectApp(position: Int){
        selectedApps.add(appList[position])
    }

    fun deselectApp(position: Int){
        selectedApps.remove(appList[position])
    }

    fun resetSelection(){
        selectedApps.clear()
    }
}