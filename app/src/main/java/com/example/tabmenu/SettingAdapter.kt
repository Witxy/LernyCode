package com.example.tabmenu

import android.R.attr.fragment
import android.R.attr.tag
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class SettingAdapter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_adapter)
        val myTool = findViewById<Toolbar>(R.id.settingsToolBar)


        myTool.setNavigationOnClickListener {
            finish()
        }




        val localIntent = intent


        when (localIntent.getIntExtra("type", 0)) {
            1 -> {
                val manager: FragmentManager = supportFragmentManager
                val ft: FragmentTransaction = manager.beginTransaction()
                ft.replace(R.id.fragment2, RedProfileFragment())
                ft.commitAllowingStateLoss()
            }
            2 -> {
                val manager: FragmentManager = supportFragmentManager
                val ft: FragmentTransaction = manager.beginTransaction()
                ft.replace(R.id.fragment2, ChangePassFragment())
                ft.commitAllowingStateLoss()
            }
        }

    }


}