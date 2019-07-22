package edu.rosehulman.zous_liua1.shortcut

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.action_card.view.*

class ActionVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val timeText = itemView.time_action_card
    private val typeText = itemView.type_action_card
    private lateinit var action: Action

    fun bind(action: Action){
        this.action = action
        timeText.text = convertTime(action.time)
        if(action.type == Action.ACTION_TYPE.TAP){
            typeText.text = Constants.TYPE_TAP
        }else{
            typeText.text = Constants.TYPE_SWIPE
        }
    }

    private fun convertTime(time: Int): String{
        //TODO: Implement this time converter after implementation plan confirmed
        return ""
    }
}