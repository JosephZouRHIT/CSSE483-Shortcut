package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActionAdapter(var context: Context): RecyclerView.Adapter<ActionVH>() {
    private val actionList = ArrayList<Action>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionVH {
        val view = LayoutInflater.from(context).inflate(R.layout.action_card, parent, false)
        return ActionVH(view)
    }

    override fun getItemCount(): Int = actionList.size

    override fun onBindViewHolder(holder: ActionVH, position: Int) {
        holder.bind(actionList[position])
    }
}