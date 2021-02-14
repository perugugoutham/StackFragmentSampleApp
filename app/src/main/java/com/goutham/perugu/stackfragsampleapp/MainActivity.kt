package com.goutham.perugu.stackfragsampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goutham.perugu.stackfragment.framework.StackFragmentsHolder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)

        val stackFragment = navHostFragment!!.childFragmentManager.fragments[0] as StackFragmentsHolder
        stackFragment.addFragment(Frag1Bottom())
        stackFragment.addFragment(Frag2Bottom())
        stackFragment.addFragment(Frag3Bottom())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
        val stackFragment = navHostFragment!!.childFragmentManager.fragments[0] as StackFragmentsHolder
        stackFragment.onBackPressed()
    }

}