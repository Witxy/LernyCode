    package com.example.tabmenu

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


    class MainActivity : AppCompatActivity() {

        private lateinit var mAuth: FirebaseAuth
        var isNightModeOn: Boolean = false
        var lesson1: Boolean = false
        lateinit var sharedPreferences: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.SettingsFragmentStyle);
        val botNav= findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragment)

        val achieveEditor  = getSharedPreferences("achieve", MODE_PRIVATE).edit()
        achieveEditor.putBoolean("achieve1", true)
        achieveEditor.apply()

        botNav.setupWithNavController(navController)
        //botNav.selectedItemId = R.id.teachFragment

        val image = findViewById<ImageView>(R.id.image_toolbar)
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        sharedPreferences = appSettingPrefs.edit()
       isNightModeOn = appSettingPrefs.getBoolean("NightMode", false)



        if(isNightModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }





        fun logout(view: View)
        {
            FirebaseAuth.getInstance().signOut();
            val myIntent = Intent(this, LoginActivity::class.java)
            startActivity(myIntent)
            finish();
        }
        fun adapter(view: View, extra: Int)
        {
            val myIntent = Intent(this, SettingAdapter::class.java)
            myIntent.putExtra("type", extra)
            startActivity(myIntent)
        }



        fun openStat(view: View)
        {
            val myIntent = Intent(this, StatActivity::class.java)
            startActivity(myIntent)
        }
        fun openAchieve(view: View)
        {
            val myIntent = Intent(this, AchieveActivity::class.java)
            startActivity(myIntent)
        }

        override fun onSupportNavigateUp(): Boolean {
            val navCont = findNavController(R.id.fragment)
            return navCont.navigateUp() || super.onSupportNavigateUp()
        }


        fun settOpen(item: MenuItem)
        {
            findNavController(R.id.fragment).navigate(R.id.action_profileFragment_to_settingsFragment)
        }


        fun modeChange(item: MenuItem)
        {
            if(isNightModeOn)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.putBoolean("NightMode", false)
                sharedPreferences.apply()
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.putBoolean("NightMode", true)
                sharedPreferences.apply()
            }



        }



        fun teachType(view: View)
        {
            val temp: String = view.tag.toString()
            val typePer: Int = temp.toInt()
            teachOpen(typePer)

        }

        fun taskType(view: View)
        {
            val temp: String = view.tag.toString()
            val typePer: Int = temp.toInt()
            taskOpen(typePer)

        }
        fun taskComplete(view: View)
        {
            val temp: String = view.tag.toString()
            val typePer: Int = temp.toInt()
            taskCompleteOpen(typePer)

        }



        private fun teachOpen(typePer: Int)
        {
            val myIntent = Intent(this, TeachActivity::class.java)
            myIntent.putExtra("type", typePer)
            startActivity(myIntent)
        }

        private fun taskOpen(typePer: Int)
        {
            val myIntent = Intent(this, TaskActivity::class.java)
            myIntent.putExtra("type", typePer)
            startActivity(myIntent)
        }
        private fun taskCompleteOpen(typePer: Int)
        {
            val myIntent = Intent(this, TaskComplete::class.java)
            myIntent.putExtra("type", typePer)
            startActivity(myIntent)
        }




    }