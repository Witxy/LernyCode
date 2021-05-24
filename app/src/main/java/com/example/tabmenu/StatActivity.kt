package com.example.tabmenu

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class StatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)



        val countTask: TextView = findViewById(R.id.statCountTaskCode)
        val prefs = getSharedPreferences("task", MODE_PRIVATE)
        val editor  = getSharedPreferences("task", MODE_PRIVATE).edit()
//        editor.remove("taskCount").commit()
//        editor.remove("task1").commit()
//       editor.remove("task2").commit()
        val tempString: String = prefs.getInt("taskCount",0).toString()+"/6шт"
        countTask.text = tempString

    }


}