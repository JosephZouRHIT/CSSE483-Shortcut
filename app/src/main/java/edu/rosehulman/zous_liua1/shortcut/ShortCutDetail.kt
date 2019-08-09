package edu.rosehulman.zous_liua1.shortcut


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_shortcut_detail.view.*

private const val ARG_SHORTCUT = "shortcut"
private const val ARG_POSITION = "position"

class ShortCutDetail : Fragment() {
    private lateinit var shortcut: ShortCut
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shortcut = it.getParcelable<ShortCut>(ARG_SHORTCUT)!!
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shortcut_detail, container, false)
        view.shortcut_title_detail.text = shortcut.title
        view.description_detail.text = shortcut.description
        if(shortcut.isLocked){
            view.lock_state_detail.text = getString(R.string.locked_state)
        }else{
            view.lock_state_detail.text = getString(R.string.unlocked_state)
        }
        view.app_list_detail.adapter = AppAdapter(context!!, position, shortcut, null, false)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        view.app_list_detail.layoutManager = manager
        view.app_list_detail.setHasFixedSize(true)
        (context!! as MainActivity).title = shortcut.title
        val fab = (context!! as MainActivity).fab
        fab.setImageResource(R.drawable.ic_edit_black_24dp)
        fab.setOnClickListener {
            val ft = (context!! as MainActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, ShortCutEdit.newInstance(shortcut, position))
            ft.addToBackStack("edit")
            ft.commit()
        }
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(shortcut: ShortCut, position: Int) =
            ShortCutDetail().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SHORTCUT, shortcut)
                    putInt(ARG_POSITION, position)
                }
            }
    }
}
