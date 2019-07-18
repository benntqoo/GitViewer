package com.jrtou.gitviewer.views.activities

import android.os.Bundle
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.helpers.SHFragmentHelper
import com.jrtou.gitviewer.views.fragments.HomeFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

class MainActivity : RxAppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val fragmentHelper = SHFragmentHelper(supportFragmentManager, R.id.flMain)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentHelper.addFragment(HomeFragment.newInstance())
    }
}

