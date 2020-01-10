package com.iww.classifiedolx

import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.iww.classifiedolx.Fragments.FavouriteFragment
import com.iww.classifiedolx.Fragments.HomeFragment
import com.iww.classifiedolx.Fragments.MyAccountFragment
import com.iww.classifiedolx.Fragments.MyAdsFragment
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.Utilities.SharedPref
import com.iww.classifiedolx.Utilities.Utility
import kotlinx.android.synthetic.main.activity_login_signup_screen.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnFragmentInteractionListener, FragmentManager.OnBackStackChangedListener {
    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }

    override fun onFragmentInteraction(uri: Uri?) {

    }

    val NUM_PAGES = 5
    private var mPagerAdapter: ScreenSlidePagerAdapter? = null
    var sharedPref: SharedPref? = null
    var TAG = "MainActivity "
    var choosedItem = 0
    private  var toggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        sharedPref=SharedPref(this@MainActivity)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)

        view_pager.offscreenPageLimit = NUM_PAGES!!
        view_pager.adapter = mPagerAdapter
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        view_pager!!.setPagingEnabled(false)
        supportFragmentManager.addOnBackStackChangedListener(this)
        supportActionBar!!.title = "Home"
  setTitleBarData()

        if(sharedPref!!.userId!="") {
            val header = navView.getHeaderView(0)
            var tv_loggedEmail = header.findViewById<TextView>(R.id.tv_sidebarEmail)
            tv_loggedEmail.text = "" + sharedPref!!.email
            var tv_loggedName = header.findViewById<TextView>(R.id.tv_sidebarName)
            tv_loggedName.text = "Welcome :  " + sharedPref!!.name
        }
    }

    private fun setTitleBarData() {
     if(supportFragmentManager.backStackEntryCount >1){
                toggle?.isDrawerIndicatorEnabled = false
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                toggle?.toolbarNavigationClickListener = navigationBackPressListener
            }
            else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
                toggle?.isDrawerIndicatorEnabled = true
                toggle?.toolbarNavigationClickListener = toggle?.toolbarNavigationClickListener
            }
  }
    private val navigationBackPressListener = View.OnClickListener { v ->
        onBackPressed()

    }
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_navigation_home -> {
                    supportActionBar!!.title = "Home"
                    if (supportFragmentManager.backStackEntryCount > 0)
                        supportFragmentManager.popBackStack()
                    view_pager.setCurrentItem(0, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_navigation_favourite -> {
                    supportActionBar!!.title = "Favourite"
                    if (supportFragmentManager.backStackEntryCount > 0)
                        supportFragmentManager.popBackStack()
                    view_pager.setCurrentItem(1, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_navigation_myads -> {
                    supportActionBar!!.title = "My Ads"
                    if (supportFragmentManager.backStackEntryCount > 0)
                        supportFragmentManager.popBackStack()
                    view_pager.setCurrentItem(2, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_navigation_myaccount -> {
                    supportActionBar!!.title = "My Account"
                    if (supportFragmentManager.backStackEntryCount > 0)
                        supportFragmentManager.popBackStack()
                    view_pager.setCurrentItem(3, true)
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        else if( supportFragmentManager.backStackEntryCount>1) {
            super.onBackPressed()

        }
        else if (view_pager.currentItem === 0) {
            super.onBackPressed()
        } else if (view_pager.currentItem !== 0) {
            //    bottomNavigation.selectedItemId = view_pager.currentItem - 1;
            view_pager.setCurrentItem(view_pager.currentItem - 1, true)
            bottomNavigation.getMenu().getItem(view_pager.currentItem).setChecked(true);
            if (view_pager.currentItem == 3)
                supportActionBar!!.title = "My Account"
            else if (view_pager.currentItem == 2)
                supportActionBar!!.title = "My Ads"
            else if (view_pager.currentItem == 1)
                supportActionBar!!.title = "Favourite"
            else if (view_pager.currentItem == 0)
                supportActionBar!!.title = "Home"

        } else {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
  var yourdrawable = menu.getItem(1).getIcon(); // change 0 with 1,2 ...
    yourdrawable.mutate();
    yourdrawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_call -> {
                Utility.callDialog(this,"9872668896")
                return true
            }
            R.id.action_addNewAdvert -> {
                Utility.snackBar(drawer_layout, "You need to login first"  )
                Toast.makeText(this@MainActivity,"You need to login first, For add",Toast.LENGTH_LONG).show()

                if (sharedPref!!.userId == "") {
                    Utility.snackBar(drawer_layout, "You need to login first"  )
Toast.makeText(this@MainActivity,"You need to login first, For add",Toast.LENGTH_LONG).show()
                   }
                 return true
            }


            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                view_pager.currentItem = 0
                supportActionBar!!.title = "Home"
                bottomNavigation.getMenu().getItem(0).setChecked(true);
            }
            R.id.nav_myAccount -> {
                view_pager.currentItem =  3
                supportActionBar!!.title = "My Account"
                bottomNavigation.getMenu().getItem(3).setChecked(true);
            }
            R.id.nav_logout -> {
                Utility.alertLogoutDialog(this@MainActivity,"Do you want to logout?")

            }
            R.id.nav_my_ads -> {
                view_pager.currentItem = 2
                supportActionBar!!.title = "My Ads"
                bottomNavigation.getMenu().getItem(2).setChecked(true);
            }
            R.id.nav_share -> {
                val shareTxtBody =   "The free online marketplace app from Classified assures you of a great shopping experience with a lighter app where you can Buy and Sell, faster load time & wide selection across categories  \n Coming soon ... On Android "
                ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Share Classified App")
                    .setText(shareTxtBody)
                    .startChooser()
            }
            R.id.nav_favourite -> {
                view_pager.currentItem =1
                supportActionBar!!.title = "Favourite"
                bottomNavigation.getMenu().getItem(1).setChecked(true);
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        var fragment: Fragment? = null

        override fun getCount(): Int {
            return NUM_PAGES!!
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    fragment = HomeFragment()
                    return fragment!!
                }
                1 -> {

                    fragment = FavouriteFragment()
                    return fragment!!
                }

                2 -> {
                    fragment = MyAdsFragment()
                    return fragment!!
                }
                3 -> {
                    fragment = MyAccountFragment()
                    return fragment!!
                }

            }
            fragment = HomeFragment()
            return fragment!!
        }

    }


}
