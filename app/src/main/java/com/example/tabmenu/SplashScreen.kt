package com.example.tabmenu

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val user  = mAuth.currentUser

        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPreferences: SharedPreferences.Editor = appSettingPrefs.edit()
       val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)


        if(isNightModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        Handler(Looper.getMainLooper()).postDelayed({
            if (user != null) {
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
                finish()
            }
            else
            {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)


    }
}