package com.example.tabmenu

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TeachActivity : AppCompatActivity() {


    private var count: Int = 1
    private var pageCount: Int = 1
    private var curPage: Int = 1
    private var keyWords: ArrayList<String> = ArrayList()
    private var radioList: ArrayList<Int> = ArrayList()
    var testAnswer: Int = 0
    var isTest: Boolean = false
    var prevPage: Boolean = false
    var textArray: ArrayList<String> = ArrayList()
    var elementCount: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teach)
        val myTool = findViewById<Toolbar>(R.id.myToolBar)
//        setSupportActionBar(myTool)
        keyWords.addAll(listOf("abstract", "as","base","bool","break","byte","case","catch","char","checked","class","const","continue","decimal","default","delegate","do"
                ,"double","else","enum","event","expicit","extern","false","finally","fixed","float","for","foreach","goto","if","impicit","in","int","interface"
                ,"is","lock","long","namespace","new","null","object","operator","out","override","params","private","protected","public","readonly"
                ,"ref","return","sbyte","sealed","short","sizeof","stackalloc","static","string","struct","switch","this","throw","true","try","typeof","uint","ulong","unchecked"
                ,"unsafe","ushort","using","virtual","void","volatile","while"))


        //???????????? ??????????
        myTool.setNavigationOnClickListener {

            finish()

        }
        val nextBut: FloatingActionButton = findViewById(R.id.nextButton)
        val prevBut: FloatingActionButton = findViewById(R.id.prevButton)
        val localIntent = intent



        when (localIntent.getIntExtra("type",0))
        {
            1 -> pageManager(1, 3, "lesson1.txt")
            2 -> pageManager(2, 7, "lesson2.txt")
            3->pageManager(3, 7, "lesson3.txt")
            4->pageManager(4, 3, "lesson4.txt")
            5->pageManager(5, 5, "lesson5.txt")
        }


        nextBut.setOnClickListener{
            nextPage()
        }
        prevBut.setOnClickListener{
            val progress: ProgressBar = findViewById(R.id.progressBar)
            if(curPage!=1) {
                curPage -= 2
                progress.progress = curPage+1
                prevPage = true
                nextPage()
            }
        }


    }


    private fun pageManager(argCount: Int, argPageCount: Int, fName: String)
    {
        val progress: ProgressBar = findViewById(R.id.progressBar)
        count = argCount
        pageCount = argPageCount
        progress.max = pageCount
        progress.progress = curPage
        readSetting(fName)

    }

    /*
    private fun lessonPageProceed()
    {
        //readSetting("lesson1.txt")
        when(count)
        {
            1->lesson()
            2->lesson2()
            3->lesson3()
            4->lesson4()
            5->lesson5()
        }
    }

     */


    private fun readSetting(fName: String)
    {
        val inputString = application.assets.open(fName).bufferedReader().use { it.readText() }
        val settingList: List<String> = inputString.split("\n","%???%","|\\|", "\r")

        for(i in settingList.indices)
        {
            if(settingList[i]!="")
            {
                textArray.add(settingList[i])
            }
        }
            lesson()
    }
    private fun lesson()
    {
        var tempPage = curPage-1
        elementCount.add(tempPage,0)
        if(tempPage-1==-1)
            tempPage++
        for(i in elementCount[tempPage-1] until textArray.size)
        {
            if(textArray[i] =="text")
            {
                lessonTextAdd(textArray[i+1])
            }
            else if(textArray[i]== "test")
            {
                testAdd(textArray[i+1],textArray[i+2],textArray[i+3],textArray[i+4],textArray[i+5].toInt())
            }
            else if(textArray[i]== "code")
            {
                lessonCodeAdd(textArray[i+1])
            }
            else if(textArray[i]== "out")
            {
                lessonOutAdd(textArray[i+1])
            }
            else if(textArray[i] =="page")
            {
                elementCount[curPage-1] = i+1
                break
            }
            elementCount[curPage-1] = i

        }

    }

    private fun lessonTextAdd(text: String)
    {
        val field: LinearLayout = findViewById(R.id.textField)
        val textV = TextView(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        val rowSplit = text.split("\\n")
        params.setMargins(10, 10, 10, 10)
        for(i in rowSplit.indices) {
            textV.append(rowSplit[i])
            textV.append("\n")

        }
        textV.textSize = 14F
        textV.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textV.typeface = ResourcesCompat.getFont(this, R.font.verdana)
        textV.setTextColor(ContextCompat.getColor(this, R.color.white))
        textV.layoutParams = params
        field.addView(textV)



    }

    private fun lessonCodeAdd(text: String)
    {
        val field: LinearLayout = findViewById(R.id.textField)
        val textV = TextView(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        val rowSplit = text.split("\\n")

        var spanWord: Spannable
        var literalText: String

        for(i in rowSplit.indices)
        {
            textV.append("\n")
            var parts = rowSplit[i].split(" ",";")
            spanWord = SpannableString((i+1).toString())
            spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.white_inactive)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textV.append(spanWord)
            textV.append("   ")

            for(j in 0 until parts.size)
            {
                if(keyWords.contains(parts[j]))
                {
                    textV.append(" ")
                    spanWord= SpannableString(parts[j])
                    spanWord.setSpan(ForegroundColorSpan(Color.parseColor("#569cd6")), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    textV.append(spanWord)

                }
                else
                {
                    textV.append(" ")

                    val textSplit = rowSplit[i].split("\"", "\'")
                    if(textSplit.size != 1) {
                        for (k in textSplit.indices) {
                            if (k % 2 == 0) {
                                spanWord = SpannableString(textSplit[k])
                                spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                textV.append(spanWord)
                            } else {
                                spanWord = SpannableString("\"")
                                spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_string)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                textV.append(spanWord)
                                spanWord = SpannableString(textSplit[k])
                                spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_string)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                textV.append(spanWord)
                                spanWord = SpannableString("\"")
                                spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_string)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                                textV.append(spanWord)
                            }
                        }
                        break

                    }
                    else
                    {
                        spanWord = SpannableString(parts[j])
                        spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        textV.append(spanWord)
                    }

                }
                if(j==parts.size-2)
                {
                    for(k in 0..rowSplit[i].length-1)
                    {
                        literalText = rowSplit[i]
                        if(literalText[k] == ';') {
                            spanWord = SpannableString(";")
                            spanWord.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)), 0, spanWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            textV.append(spanWord)
                            break
                        }
                    }
                }


            }

        }
        textV.append("\n")
        params.setMargins(10, 5, 10, 20)
        textV.layoutParams = params
        textV.textSize = 14F
        textV.background = ContextCompat.getDrawable(this, R.color.dark_grey)
        textV.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textV.layoutParams = params
        field.addView(textV)

    }
    private fun lessonOutAdd(text: String)
    {
        val field: LinearLayout = findViewById(R.id.textField)
        val textB = TextView(this)
        val textV = TextView(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        val rowSplit = text.split("\\n")
        textB.text = "?????????? ???? ??????????????:"
        textB.textSize = 14F
        textB.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textB.setTextColor(ContextCompat.getColor(this, R.color.white))
        textB.layoutParams = params
        field.addView(textB)

        params.setMargins(10, 10, 10, 10)
        for(i in rowSplit.indices) {
            textV.append(rowSplit[i])
            textV.append("\n")

        }
        textV.textSize = 14F
        textV.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textV.setTextColor(ContextCompat.getColor(this, R.color.output_white))
        textV.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        textV.layoutParams = params
        field.addView(textV)
    }
    private fun testAdd(text1: String, text2: String,text3: String,text4: String,rightAnswer: Int)
    {
        val field: LinearLayout = findViewById(R.id.textField)
        val group: RadioGroup = RadioGroup(this)
        val radioButton1 = RadioButton(this)
        val radioButton2 = RadioButton(this)
        val radioButton3 = RadioButton(this)
        val radioButton4 = RadioButton(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        testAnswer = rightAnswer
        isTest = true
        group.background = ContextCompat.getDrawable(this, R.color.dark_grey)
        group.gravity = Gravity.CENTER
        params.setMargins(40,20,40,20)
        group.layoutParams = params


        radioButton1.setTextColor(ContextCompat.getColor(this, R.color.white))
        radioButton2.setTextColor(ContextCompat.getColor(this, R.color.white))
        radioButton3.setTextColor(ContextCompat.getColor(this, R.color.white))
        radioButton4.setTextColor(ContextCompat.getColor(this, R.color.white))
        radioButton1.buttonTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        radioButton2.buttonTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        radioButton3.buttonTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        radioButton4.buttonTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        params.setMargins(20,20,20,20)
        radioButton1.layoutParams = params
        radioButton2.layoutParams = params
        radioButton3.layoutParams = params
        radioButton4.layoutParams = params
        radioButton1.id = View.generateViewId()
        radioButton2.id = View.generateViewId()
        radioButton3.id = View.generateViewId()
        radioButton4.id = View.generateViewId()
        radioList.add(radioButton1.id)
        radioList.add( radioButton2.id)
        radioList.add(radioButton3.id)
        radioList.add(radioButton4.id)


        radioButton1.text = text1
        group.addView(radioButton1)
        radioButton2.text = text2
        group.addView(radioButton2)
        radioButton3.text = text3
        group.addView(radioButton3)
        radioButton4.text = text4
        group.addView(radioButton4)
        field.addView(group)

    }

    private fun nextPage()
    {
        val field: LinearLayout = findViewById(R.id.textField)
        val progress: ProgressBar = findViewById(R.id.progressBar)
        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
        val sharedPreferences = appSettingPrefs.edit()
        if(curPage!=pageCount) {
            if (!prevPage) {
                if (isTest) {
                    if (field.findViewById<RadioButton>(radioList[testAnswer - 1]).isChecked) {
                        field.removeAllViews()
                        curPage++
                        progress.progress = curPage
                        lesson()
                        radioList.clear()
                        isTest = false
                    } else {
                        for (i in 0..radioList.size - 1) {
                            if (field.findViewById<RadioButton>(radioList[i]).isChecked) {
                                field.findViewById<RadioButton>(radioList[i]).setTextColor(ContextCompat.getColor(this, R.color.red))
                                field.findViewById<RadioButton>(radioList[i]).buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
                                break
                            }
                        }
                    }


                } else {

                    field.removeAllViews()
                    curPage++
                    progress.progress = curPage
                    lesson()
                }

            } else {
                radioList.clear()
                isTest = false
                prevPage = false
                field.removeAllViews()
                curPage++
                lesson()
            }
        }
        else
        {
            val editor  = getSharedPreferences("task", MODE_PRIVATE).edit()
            val prefs = getSharedPreferences("task", MODE_PRIVATE)
            val achieveEditor  = getSharedPreferences("achieve", MODE_PRIVATE).edit()
            achieveEditor.putBoolean("achieve2", true)
            achieveEditor.apply()
            when(count) {
                1 -> {
                    editor.putBoolean("lesson1", true)
                }
                2 -> {
                    editor.putBoolean("lesson2", true)
                }
                3 -> {
                    editor.putBoolean("lesson3", true)
                }
                4 -> {
                    editor.putBoolean("lesson4", true)
                }
                5 -> {
                    editor.putBoolean("lesson5", true)
                }

            }
            var counter: Int = 0
            editor.apply()
            if(prefs.getBoolean("lesson1",false))
                counter++
            if(prefs.getBoolean("lesson2",false))
                counter++
            if(prefs.getBoolean("lesson3",false))
                counter++
            if(prefs.getBoolean("lesson4",false))
                counter++
            if(prefs.getBoolean("lesson5",false))
                counter++

            editor.putInt("lessonCount", counter)
            editor.apply()

            finish()
        }
    }




}