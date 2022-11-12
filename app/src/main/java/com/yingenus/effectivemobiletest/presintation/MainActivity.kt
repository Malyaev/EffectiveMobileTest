package com.yingenus.effectivemobiletest.presintation

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.yingenus.effectivemobiletest.R

class MainActivity : AppCompatActivity() {

    private var buttonViewHelper : BottomViewHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        buttonViewHelper = BottomViewHelper(findViewById(R.id.bottom_navigation_view))
        buttonViewHelper!!.selectItem(BottomViewHelper.ItemType.SHOP)
        setUpWithNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        buttonViewHelper = null
    }

    private fun setUpWithNavigation(){
        val navHost : NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        navController.addOnDestinationChangedListener( object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {

                when(destination.id){
                    com.yingenus.feature_showcase.R.id.main_showcase -> {
                        Log.d("MainActivity","show")
                        buttonViewHelper!!.hide(false)
                        buttonViewHelper!!.selectItem(BottomViewHelper.ItemType.SHOP)
                    }
                    else -> {
                        Log.d("MainActivity","hide")
                        buttonViewHelper!!.hide(true)
                    }
                }
            }
        })
    }
}