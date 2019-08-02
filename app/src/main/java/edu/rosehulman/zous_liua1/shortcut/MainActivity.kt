package edu.rosehulman.zous_liua1.shortcut

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ShortcutList.OnShortcutListener
    , ShortCutEdit.OnAppClickedListener {

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var isDrawn = true
        var intent: Intent? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            isDrawn = Settings.canDrawOverlays(this)
            if (!isDrawn){
                startActivity(intent)
            }
        }

        fab = findViewById(R.id.fab)
        resetFab()
        val drawerLayout: DrawerLayout = findViewById(R.id.main)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        switchToShortcutListFragment()
    }

    private fun switchToShortcutListFragment() {
        val ft = supportFragmentManager.beginTransaction()
        val fragment = ShortcutList()
        ft.replace(R.id.fragment_container, fragment)
        ft.commit()
    }

    private fun switchToCreateShortcutFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, InstalledAppList())
        ft.addToBackStack("installedApps")
        ft.commit()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.main)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.drawer_list -> {
                switchToShortcutListFragment()
            }

            R.id.drawer_settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.main)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onShortcutSelected(shortCut: ShortCut) {
        val fragment = ShortCutDetail()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main, fragment)
        ft.addToBackStack("detail")
        ft.commit()
    }

    override fun onAppClicked(app: App) {
        val pkgName = app.pkgInfo.packageName
        val intent = packageManager.getLaunchIntentForPackage(pkgName)
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Cannot launch app", Toast.LENGTH_LONG).show()
        }
    }

    fun resetFab() {
        fab.setImageResource(R.drawable.ic_add_white)
        fab.setOnClickListener {
            switchToCreateShortcutFragment()
            val service = Intent(this, OverlayService::class.java)
            startService(service)
        }
    }
}
