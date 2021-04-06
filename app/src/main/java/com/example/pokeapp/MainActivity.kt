package com.example.pokeapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokeapp.ui.login.LoginViewModel
import com.example.pokeapp.ui.login.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var _menu: Menu

//    private lateinit var loginViewModel: LoginViewModel
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
//            .get(LoginViewModel::class.java)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_team, R.id.nav_storage
//                R.xml.root_preferences
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.label == getString(R.string.app_name)) {
                toolbar.title = destination.label
                toolbar.navigationIcon = null
            }
        }

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
////            navController.navigate(R.id.action_global_settingsFragment)
//            navController.navigate(R.id.action_global_regionFragment)
//        }


        loginViewModel.isLoggedIn.observe(this,
            androidx.lifecycle.Observer {
                Log.d("isLoggedIn main", it.toString())
                if (it) {
//                    fab.visibility = View.VISIBLE
                    if (this::_menu.isInitialized) {
                        visibleOptionMenu(_menu, true)
                    }
                } else {
//                    fab.visibility = View.GONE
                    if (this::_menu.isInitialized) {
                        visibleOptionMenu(_menu, false)
                    }
                }
            })
    }

    private fun visibleOptionMenu(menu: Menu, isVisible: Boolean) {
        menu.findItem(R.id.action_logout).isVisible = isVisible
        menu.findItem(R.id.action_settings).isVisible = isVisible
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        _menu = menu
        visibleOptionMenu(menu, false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        val navController = findNavController(R.id.nav_host_fragment)
        return when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
                true
            }
            R.id.action_logout -> {
                loginViewModel.logout()
//                System.exit(0);
//                finishAffinity()
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.temporary_title))
            .setMessage(getString(R.string.temporary_message))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                // pass
            }
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                finishAffinity()
            }
            .show()
    }

}