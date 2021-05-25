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


        //кнопка назад
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
            5->pageManager(5, 4, "lesson5.txt")
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
        val settingList: List<String> = inputString.split("\n","%№%","|\\|", "\r")

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

/*
    private fun lesson1()
    {
        when(curPage)
        {
            1->{
                lessonTextAdd(resources.getString(R.string.lesson1_text_page1_1))

            }
            2->{
                lessonTextAdd(resources.getString(R.string.lesson1_text_page2_1))
                testAdd("C++ и Java","Kotlin и Java","Javascript и PHP","Ни к каким",1)
            }

            3->{
                lessonTextAdd(resources.getString(R.string.lesson1_text_page3_1))
            }
            4->{
                lessonTextAdd(resources.getString(R.string.lesson1_text_page4_1))
            }
        }

    }

    private fun lesson2()
    {
        when (curPage)
        {
            1->{
                lessonTextAdd(resources.getString(R.string.lesson2_text_page1_1))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page1_1))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page1_2))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page1_2))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page1_3))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page1_3))
            }
            2->{
                lessonTextAdd(resources.getString(R.string.lesson2_text_page2_1))
                testAdd("В квадратные скобки","В круглые скобки","В фигурные скобки","Блоки кода не выделяются скобками",3)
            }
            3->{
                lessonTextAdd(resources.getString(R.string.lesson2_text_page3_1))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page3_1))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page3_2))
            }
            4->{
                lessonTextAdd(resources.getString(R.string.lesson2_text_page4_1))
            }
            5->{
                lessonTextAdd(resources.getString(R.string.lesson2_text_page5_1))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page5_1))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page5_2))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page5_2))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page5_3))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page5_3))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page5_4))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page5_4))
                lessonTextAdd(resources.getString(R.string.lesson2_text_page5_5))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page5_5))
            }
            6-> {
                lessonTextAdd(resources.getString(R.string.lesson2_text_page6_1))
                testAdd("1Name","jfsndfmds","my name","name",4)
            }
            7->{
                lessonTextAdd(resources.getString(R.string.lesson2_text_page7_1))
                lessonCodeAdd(resources.getString(R.string.lesson2_code_page7_1))
                consoleOutputAdd(resources.getString(R.string.lesson2_out_page7_1))
            }
        }
    }
    private fun lesson3()
    {
        when(curPage) {
            1->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page1_1))

            }
            2->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page2_1))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page2_1))
            }
            3->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page3_1))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page3_1))
                lessonTextAdd(resources.getString(R.string.lesson3_text_page3_2))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page3_2))
                lessonTextAdd(resources.getString(R.string.lesson3_text_page3_3))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page3_3))
            }
            4->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page4_1))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page4_1))
                lessonTextAdd(resources.getString(R.string.lesson3_text_page4_2))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page4_2))
            }
            5->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page5_1))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page5_1))
            }
            6->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page6_1))
                lessonCodeAdd(resources.getString(R.string.lesson3_code_page6_1))
                lessonTextAdd(resources.getString(R.string.lesson3_text_page6_2))
            }
            7->{
                lessonTextAdd(resources.getString(R.string.lesson3_text_page7_1))
                testAdd("Console.WriteLine(true);","Console.WriteLine(505);","\'2\'","Console.WriteLine(\"hello\");",2)
            }
        }
    }

    private fun lesson4() {
        when (curPage) {
            1 -> {
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_1))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_1))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_2))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_2))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_3))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_3))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_4))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_4))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_5))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_5))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_6))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_6))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_7))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_7))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_8))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_8))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_9))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_9))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_10))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_10))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_11))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_11))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page1_12))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page1_12))
            }
            2 -> {
                lessonTextAdd(resources.getString(R.string.lesson4_text_page2_1))
                lessonCodeAdd(resources.getString(R.string.lesson4_code_page2_1))
                lessonTextAdd(resources.getString(R.string.lesson4_text_page2_2))
                consoleOutputAdd(resources.getString(R.string.lesson4_out_page2_1))
            }
            3 -> {
                lessonTextAdd(resources.getString(R.string.lesson4_text_page3_1))
                testAdd("ulong","byte","short","int",4)
            }
        }
    }

    private fun lesson5()
    {
        when(curPage)
        {
            1->{
                lessonTextAdd(resources.getString(R.string.lesson5_text_page1_1))
                lessonCodeAdd(resources.getString(R.string.lesson5_code_page1_1))
                consoleOutputAdd(resources.getString(R.string.lesson5_out_page1_1))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page1_2))
                lessonCodeAdd(resources.getString(R.string.lesson5_code_page1_2))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page1_3))
                consoleOutputAdd(resources.getString(R.string.lesson5_out_page1_2))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page1_4))
                lessonCodeAdd(resources.getString(R.string.lesson5_code_page1_3))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page1_5))

            }
            2->{
                lessonTextAdd(resources.getString(R.string.lesson5_text_page2_1))
                testAdd("Для вывода в одной строке несколько переменных","Для вывода чисел","Для вывода переменных уппорядоченно","Несуществующий термин",1)
            }
            3->{
                lessonTextAdd(resources.getString(R.string.lesson5_text_page3_1))
                lessonCodeAdd(resources.getString(R.string.lesson5_code_page3_1))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page3_2))
                consoleOutputAdd(resources.getString(R.string.lesson5_out_page3_1))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page3_3))
                lessonCodeAdd(resources.getString(R.string.lesson5_code_page3_2))
                lessonTextAdd(resources.getString(R.string.lesson5_text_page3_4))
                consoleOutputAdd(resources.getString(R.string.lesson5_out_page3_2))
            }
            4->{
                lessonTextAdd(resources.getString(R.string.lesson5_text_page4_1))
                testAdd("Convert.ToDouble()","Convert.ToDecimal()","Convert.ToInt32() ","Ничего из вышеперечисленного",3)

            }
        }
    }

 */

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
        textB.text = "Вывод на консоль:"
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
            when(count) {
                1 -> {
                    sharedPreferences.putBoolean("lesson1", true)
                    sharedPreferences.apply()
                }
            }

            finish()
        }
    }




}