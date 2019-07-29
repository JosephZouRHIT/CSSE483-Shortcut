package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_installed_app_list.view.*

class InstalledAppList : Fragment() {
    lateinit var adapter: InstalledAppAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = InstalledAppAdapter(context!!, getInstalledApps())
        val view = inflater.inflate(R.layout.fragment_installed_app_list, container, false)
        view.installed_app_list_recycler_view.adapter = adapter
        view.installed_app_list_recycler_view.setHasFixedSize(true)
        view.installed_app_list_recycler_view.layoutManager = LinearLayoutManager(context)
        return view
    }

    private fun getInstalledApps(): ArrayList<App>{
        val list = ArrayList<App>()
        val pkgInfoList = context!!.packageManager.getInstalledPackages(0)
        for(pkg in pkgInfoList){
            if(pkg.applicationInfo.flags.and(ApplicationInfo.FLAG_SYSTEM) != 1) {
                val name = pkg.applicationInfo.loadLabel(context!!.packageManager)
                list.add(App(name.toString(), pkg))
            }
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (context!! as MainActivity).supportFragmentManager.beginTransaction().remove(this).commit()
    }

}