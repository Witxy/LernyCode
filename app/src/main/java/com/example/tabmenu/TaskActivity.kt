package com.example.tabmenu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class TaskActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val myTool = findViewById<Toolbar>(R.id.task_toolbar)


        myTool.setNavigationOnClickListener {
            finish()
        }




        val localIntent = intent


        when(localIntent.getIntExtra("type", 0))
        {
            1->taskManager(1)
            2->taskManager(2)
        }

        }


    private fun taskManager(argTask: Int)
    {
        val task1: ImageView = findViewById(R.id.task_1)
        val task2: ImageView = findViewById(R.id.task_2)
        val task3: ImageView = findViewById(R.id.task_3)
        val titleTool: TextView = findViewById(R.id.toolbar_title)
        when(argTask)
        {
            1->{
                titleTool.text= resources.getString(R.string.group1)
                task1.setImageResource(R.drawable.task_4)
                task1.setOnClickListener{

                }
                task2.setImageResource(R.drawable.task_2)
                task3.setImageResource(R.drawable.task_3)
            }
            2->{
                titleTool.text=resources.getString(R.string.group3)
                task1.setImageResource(R.drawable.task_3)
                task2.setImageResource(R.drawable.task_2)
                task3.setImageResource(R.drawable.task_1)
            }
        }



    }


}
