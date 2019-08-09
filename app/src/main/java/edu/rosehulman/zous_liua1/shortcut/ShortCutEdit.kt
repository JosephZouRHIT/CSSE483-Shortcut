package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_shortcut_detail.view.*
import kotlinx.android.synthetic.main.fragment_shortcut_edit.view.*

private const val ARG_SHORTCUT = "shortcut"
private const val ARG_POSITION = "position"

class ShortCutEdit : Fragment() {
    private lateinit var shortcut: ShortCut
    private lateinit var appListAdapter: AppAdapter
    private var listener: OnAppClickedListener? = null
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
        val view = inflater.inflate(R.layout.fragment_shortcut_edit, container, false)
        val tempSC = ShortCut(shortcut.title, shortcut.description, shortcut.appList, shortcut.isLocked)
        appListAdapter = AppAdapter(context!!, position, tempSC, listener!!, true)
        view.app_list.setHasFixedSize(true)
        view.app_list.adapter = appListAdapter
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        view.app_list.layoutManager = manager
        view.shortcut_title_edit.setText(shortcut.title)
        view.description_edit.setText(shortcut.description)
        view.is_locked_edit.isChecked = shortcut.isLocked
        (context!! as MainActivity).title = shortcut.title
        val fab = (context!! as MainActivity).fab
        fab.setImageResource(R.drawable.ic_check_white_24dp)
        fab.setOnClickListener {
            shortcut.title = view.shortcut_title_edit.text.toString()
            shortcut.description = view.description_edit.text.toString()
            shortcut.isLocked = view.is_locked_edit.isChecked
            shortcut.appList = tempSC.appList
            val fragment = when(position < 0){
                true -> {
                    (context!! as MainActivity).resetTitle()
                    val temp = (context!! as MainActivity).shortcutList
                    temp.adapter.addShortcut(shortcut)
                    temp
                }
                false -> {
                    (context!! as MainActivity).supportFragmentManager.popBackStack()
                    (context!! as MainActivity).shortcutList.adapter.editShortcut(shortcut, position)
                    ShortCutDetail.newInstance(shortcut, position)
                }
            }
            val ft = (context!! as MainActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, fragment)
            ft.commit()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAppClickedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnShortcutListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (context!! as MainActivity).resetFab()
        (context!! as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
    }

    interface OnAppClickedListener {
        fun onAppClicked(shortcut: ShortCut, position: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(shortcut: ShortCut, position: Int) =
            ShortCutEdit().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SHORTCUT, shortcut)
                    putInt(ARG_POSITION, position)
                }
            }
    }
}
