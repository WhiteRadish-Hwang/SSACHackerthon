package com.example.ssachackerthon

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.ssachackerthon.base.BaseActivity
import com.example.ssachackerthon.databinding.ActivityMainBinding
import com.example.ssachackerthon.feed.FeedFragment
import com.example.ssachackerthon.home.HomeFragment
import com.example.ssachackerthon.upload.UploadFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 앨범에 접근허용 메세지 띄움, 최초 1회만 띄움움
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
//                var page = 0
                when (item.itemId) {
                    R.id.menu_main_bottom_nav_home -> {
//                        page = R.id.menu_main_bottom_nav_home
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, HomeFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_bottom_nav_upload -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, UploadFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_bottom_nav_feed -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, FeedFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }

                }

                false
            })

    }

    fun addUploadFragment(fragment: Fragment) {
        val fmbt = supportFragmentManager.beginTransaction()

        fmbt.add(R.id.main_frame, fragment).addToBackStack(null).commitAllowingStateLoss()
    }


}