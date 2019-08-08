package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class ShortCutAdapter(var context: Context, var listener: ShortcutList.OnShortCutSelectedListener) :
    RecyclerView.Adapter<ShortCutVH>() {

    var shortCutList = ArrayList<ShortCut>()
    private var shortcutRef: CollectionReference

    init{
        val apps = ArrayList<App>()
        val sc = ShortCut("123","12345",apps, false)
        shortCutList.add(sc)

        shortcutRef = FirebaseFirestore.getInstance().collection((context as MainActivity).uid)
        shortcutRef.addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Log.e(Constants.TAG, "Listener exceeption $exception")
                return@addSnapshotListener
            }
            for(shortCutChange in snapshot!!.documentChanges){
                val shortCut = ShortCut.fromSnapShot(shortCutChange.document)
                when(shortCutChange.type){
                    DocumentChange.Type.ADDED ->{
                        shortCutList.add(0, shortCut)
                        notifyItemInserted(0)
                    }
                    DocumentChange.Type.MODIFIED ->{
                        val pos = shortCutList.indexOfFirst { shortCutChange.document.id == it.id }
                        shortCutList[pos] = shortCut
                        notifyItemChanged(pos)
                    }
                    DocumentChange.Type.REMOVED ->{
                        val pos = shortCutList.indexOfFirst { shortCutChange.document.id == it.id }
                        shortCutList.removeAt(pos)
                        notifyItemRemoved(pos)
                    }
                }
            }
        }
    }


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
        shortcutRef.add(shortcut)
    }

    fun editShortcut(shortcut: ShortCut, position: Int){
        shortcutRef.document(shortCutList[position].id).set(shortcut)
    }

    fun deleteShortcut(position: Int){
        val shortcut = shortCutList[position]
        if(shortcut.isLocked){
            Toast.makeText(context, "This shortcut is locked", Toast.LENGTH_LONG).show()
        }else{
            shortcutRef.document(shortcut.id).delete()
        }
    }
}