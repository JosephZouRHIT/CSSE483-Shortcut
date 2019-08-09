package edu.rosehulman.zous_liua1.shortcut

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_installed_app_list.view.*

private const val ARG_SHORTCUT = "shortcut"
private const val ARG_POSITION = "position"

class InstalledAppList : Fragment(){
    lateinit var adapter: InstalledAppAdapter
    private var shortcut: ShortCut? = null
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shortcut = it.getParcelable(ARG_SHORTCUT)
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = InstalledAppAdapter(context!!, getInstalledApps())
        val temp = ArrayList<App>()
        if(shortcut != null){
            for(app in shortcut!!.appList){
                temp.add(app)
            }
            adapter.selectedApps = temp
        }
        val view = inflater.inflate(R.layout.fragment_installed_app_list, container, false)
        view.installed_app_list_recycler_view.adapter = adapter
        view.installed_app_list_recycler_view.setHasFixedSize(true)
        view.installed_app_list_recycler_view.layoutManager = LinearLayoutManager(context)
        (context!! as MainActivity).title = getString(R.string.title_select_apps)
        val fab = (context!! as MainActivity).fab
        fab.setImageResource(R.drawable.ic_check_white_24dp)
        var tempSC = ShortCut(Constants.DEFAULT_SHORTCUT_NAME, Constants.DEFAULT_SHORTCUT_DESCRIPTION, ArrayList())
        if(shortcut != null){
            tempSC = shortcut!!
        }
        fab.setOnClickListener {
            tempSC.appList = adapter.selectedApps
            (context!! as MainActivity).supportFragmentManager.popBackStack()
            val ft = (context!! as MainActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, ShortCutEdit.newInstance(tempSC, position))
            ft.commit()
        }
        return view
    }

    private fun getInstalledApps(): ArrayList<App>{
        val list = ArrayList<App>()
        val pkgInfoList = context!!.packageManager.getInstalledPackages(0)
        for(pkg in pkgInfoList){
            if(pkg.applicationInfo.flags.and(ApplicationInfo.FLAG_SYSTEM) != 1) {
                val name = pkg.applicationInfo.loadLabel(context!!.packageManager)
                list.add(App(name.toString(), pkg.packageName))
            }
        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance(shortcut: ShortCut?, position: Int) =
            InstalledAppList().apply {
                arguments = Bundle().apply {
                    if(shortcut != null){
                        putParcelable(ARG_SHORTCUT, shortcut)
                    }
                    putInt(ARG_POSITION, position)
                }
            }
    }

}
