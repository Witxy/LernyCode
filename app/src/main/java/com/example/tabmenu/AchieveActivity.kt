package com.example.tabmenu

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class AchieveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achieve)


        val myTool = findViewById<Toolbar>(R.id.achieve_toolbar)
        myTool.setNavigationOnClickListener {

            finish()

        }

        val achieve1: ImageView = findViewById(R.id.img_achieve1)
        val achieve2: ImageView = findViewById(R.id.img_achieve2)
        val achieve3: ImageView = findViewById(R.id.img_achieve3)
        val achieve4: ImageView = findViewById(R.id.img_achieve4)
        val achieve5: ImageView = findViewById(R.id.img_achieve5)


        val prefs = getSharedPreferences("achieve", MODE_PRIVATE)

        if(prefs.getBoolean("achieve1",false))
            achieve1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.achieve1))
        if(prefs.getBoolean("achieve2",false))
            achieve2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.achieve2))
        if(prefs.getBoolean("achieve3",false))
            achieve3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.achieve3))
        if(prefs.getBoolean("achieve4",false))
            achieve4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.achieve1))
        if(prefs.getBoolean("achieve5",false))
            achieve5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.achieve5))


    }
}