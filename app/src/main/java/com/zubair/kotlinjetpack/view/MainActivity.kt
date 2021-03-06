package com.zubair.kotlinjetpack.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.util.PERMISSION_SEND_SMS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private fun setUpBackButtonOnTop(){
        navController = Navigation.findNavController(this, R.id.fragment_navigation_container)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBackButtonOnTop()
    }

    //asking permission code starts here
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_SEND_SMS ->{
                if(grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    notifyDetailFragment(true)
                }
                else{
                    notifyDetailFragment(false)
                }
            }
        }
    }

    private fun notifyDetailFragment(notified: Boolean) {
        val activeFragment = fragment_navigation_container.childFragmentManager.
            primaryNavigationFragment
        if(activeFragment is DetailFragment) activeFragment.onPermissionResult(notified)
    }

    fun checkTextPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                //show rationale or reason here, the second reason
                AlertDialog.Builder(this).setTitle(getString(R.string.text_permission_title))
                    .setMessage(getString(R.string.text_permission_message))
                    .setPositiveButton(getString(R.string.ask)) { dialog, which ->  requestTextPermission() }
                    .setNegativeButton(getString(R.string.no)) { dialog, which ->  notifyDetailFragment(false) }
                    .show()
            }
            else{ requestTextPermission() }
        }
        else{ notifyDetailFragment(true) }
    }

    private fun requestTextPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            PERMISSION_SEND_SMS)
    }

}
