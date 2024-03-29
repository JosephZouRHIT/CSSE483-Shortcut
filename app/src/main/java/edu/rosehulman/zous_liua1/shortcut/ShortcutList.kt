package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShortcutList : Fragment() {
    private var listener: OnShortCutSelectedListener? = null
    lateinit var adapter: ShortCutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val recyclerView = inflater.inflate(R.layout.fragment_shortcut_list, container, false) as RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        (context!! as MainActivity).resetFab()
        (context!! as MainActivity).resetTitle()
        return recyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ShortCutAdapter(activity!!, listener!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnShortCutSelectedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnShortcutListener") as Throwable
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnShortCutSelectedListener {
        fun onSCSelected(shortCut: ShortCut, position: Int)
        fun onSCSelectedForService(shortCut: ShortCut)
    }

}
