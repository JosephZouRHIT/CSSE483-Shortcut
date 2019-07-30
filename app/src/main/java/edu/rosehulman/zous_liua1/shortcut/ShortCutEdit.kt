package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_shortcut_detail.view.*
import kotlinx.android.synthetic.main.fragment_shortcut_edit.view.*

private const val ARG_SHORTCUT = "shortcut"

class ShortCutEdit : Fragment() {
    private lateinit var shortcut: ShortCut
    private lateinit var appListAdapter: AppAdapter
    private var listener: OnAppClickedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shortcut = it.getParcelable<ShortCut>(ARG_SHORTCUT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shortcut_edit, container, false)
        appListAdapter = AppAdapter(context!!, shortcut.appList, listener!!)
        view.app_list.setHasFixedSize(true)
        view.app_list.adapter = appListAdapter
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        view.app_list.layoutManager = manager
        view.shortcut_title_edit.setText(shortcut.title)
        view.description_edit.setText(shortcut.description)
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

    interface OnAppClickedListener {
        fun onAppClicked(app: App)
    }

    companion object {
        @JvmStatic
        fun newInstance(shortcut: ShortCut) =
            ShortCutEdit().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SHORTCUT, shortcut)
                }
            }
    }
}
