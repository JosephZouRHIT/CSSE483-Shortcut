package edu.rosehulman.zous_liua1.shortcut

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
   ShortCutEdit.OnAppClickedListener, ShortcutList.OnShortCutSelectedListener {

    private val RC_SIGN_IN = 1

    private val auth = FirebaseAuth.getInstance()
    private lateinit var authListener: FirebaseAuth.AuthStateListener
    lateinit var uid: String
    private lateinit var header: View

    private lateinit var fab: FloatingActionButton
    lateinit var shortcutList: ShortcutList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        shortcutList = ShortcutList()
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
        header = navView.getHeaderView(0)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        initAuthListener()
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }

    private fun switchToShortcutListFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, shortcutList)
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
            R.id.drawer_sign_out -> {
                auth.signOut()
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.main)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSCSelected(shortCut: ShortCut, position: Int) {
        val fragment = ShortCutDetail.newInstance(shortCut, position)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment)
        ft.addToBackStack("detail")
        ft.commit()
    }

    override fun onSCSelectedForService(shortCut: ShortCut) {
        val service = Intent(this, OverlayService::class.java)
        startService(service)
    }

    override fun onAppClicked(app: App) {
        val intent = packageManager.getLaunchIntentForPackage(app.pkgName)
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
        }
    }

    fun resetTitle(){
        title = getString(R.string.app_name)
    }

    private fun initAuthListener(){
        authListener = FirebaseAuth.AuthStateListener {
            val user = auth.currentUser
            if(user != null){
                uid = user.uid
                switchToShortcutListFragment()
                matchUserInfo(user)
            }else{
                uid = ""
                login()
            }
        }
    }

    private fun matchUserInfo(user: FirebaseUser){
        header.username_header.text = user.displayName
        header.textView.text = user.email
        if(user.photoUrl != null){
            Picasso.get().load(user.photoUrl).into(header.imageView)
        }
    }

    private fun login(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }
}
