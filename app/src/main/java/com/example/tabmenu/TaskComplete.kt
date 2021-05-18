package com.example.tabmenu

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.nio.file.Paths
import kotlin.math.abs


class   TaskComplete : AppCompatActivity() {

    private var count: Int = 1
    var currentPage: Int = 0
    private var pageCount: Int = 1
    private val buttonArray: ArrayList<Int> = ArrayList() //id всех кнопок
    private val rowsButArray: ArrayList<Int> = ArrayList() //id всех строк
    private val butInRowArray: ArrayList<Int> = ArrayList() //Количество кнопок в строке, массив

    private val editArray: ArrayList<Int> = ArrayList()  //id всех эдитов
    private val rowsArray: ArrayList<Int> = ArrayList() //id всех строк
    private val editInRowArray: ArrayList<Int> = ArrayList() //Количество эдитов в строке, массив
    private val rightAnswerArray: ArrayList<String> = ArrayList() //Правильные ответы
    private val rightPageArray: ArrayList<Boolean> = ArrayList()
    private val nextPageArray: ArrayList<Boolean> = ArrayList()

    private var curEditRow: Int = -1
    private var curTextRow: Int = -1
    private var curButtRow: Int = -1
    private var butIndex: Int = 0
    private var editIndex: Int = 0
    private var allTaskCount: Int = 0
    private var editInRow: Int = 0

    var buttonCounter: Int = 0
    var buttonRowCounter: Int = 0
    var rowCounter: Int = 0
    var editCounter: Int = 0
    var textCounter: Int = 0
    var rowTextCounter: Int = 0
    var rightAnswerCounter: Int = 0

    var pageCountSet: Int = 0
    var taskCount: ArrayList<Int> = ArrayList()
    var buttonRows: ArrayList<Int> = ArrayList()
    var buttonInRowCount: ArrayList<Int> = ArrayList()
    var buttonText: ArrayList<String> = ArrayList()
    var elementCount: ArrayList<Int> = ArrayList()
    var pageContent: ArrayList<String> = ArrayList()
    var editText: ArrayList<Int> = ArrayList()
    var textText: ArrayList<String> = ArrayList()
    var rowTextText: ArrayList<String> = ArrayList()
    var rowTextSpan: ArrayList<Int> = ArrayList()
    var rightAnswers: ArrayList<String> = ArrayList()
    var taskTextArray: ArrayList<String> = ArrayList()
    var codeTextArray: ArrayList<String> = ArrayList()




    private lateinit var detector: GestureDetectorCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_complete)

        detector = GestureDetectorCompat(this, DiaryGestureListener())

        val myTool = findViewById<Toolbar>(R.id.taskCompleteToolBar)


        myTool.setNavigationOnClickListener {
            finish()
        }


        val localIntent = intent


        when (localIntent.getIntExtra("type", 0)) {
            1 -> taskCompleteManager(1, 7)
            2 -> taskCompleteManager(2, 4)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
      return if(detector.onTouchEvent(event))
        {
            true
        } else {
            super.onTouchEvent(event)
        }

    }
    inner class DiaryGestureListener: GestureDetector.SimpleOnGestureListener()
    {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
                downEvent: MotionEvent?,
                moveEvent: MotionEvent?,
                velocityX: Float,
                velocityY: Float
        ): Boolean {
            val diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            val diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return  if (abs(diffX) > abs(diffY))
            {
                //левый или правый свайп
                if(abs(diffX)>SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
                {
                    if(diffX >0)
                    {
                        //правый свайп
                        this@TaskComplete.onSwipeRight()
                    }
                    else
                    {
                        //левый свайп
                        this@TaskComplete.onSwipeLeft()
                    }
                    true
                }
                else
                {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            }
            else
            {
                if(abs(diffY)>SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD)
                {
                    if(diffY >0)
                    {
                        //правый свайп
                        this@TaskComplete.onSwipeTop()
                    }
                    else
                    {
                        //левый свайп
                        this@TaskComplete.onSwipeBottom()
                    }
                    true
                }
                else
                {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }

        }

    }

    private fun onSwipeBottom() {

    }

    private fun onSwipeTop() {

    }

    private fun onSwipeRight() {
//        if(currentPage!=0) {
//            val view  = View(this)
//            currentPage--
//            resetViews(view)
//        }
    }
    private fun onSwipeLeft() {
        if(nextPageArray[currentPage]) {
            val view = View(this)
            nextPage(view)
        }

    }




    private fun taskCompleteManager(argCount: Int, argPageCount: Int) {
        val progress: ProgressBar = findViewById(R.id.taskProgressBar)
        count = argCount
        pageCount = argPageCount
        for(i in 0 until pageCount) {
            rightPageArray.add(false)
            nextPageArray.add(false)
        }
        progress.max = pageCount
        readSetting()
        pageProceed()
    }


    private fun pageProceed() {
/*
        when (count) {
            1 -> task1(1)
        }


 */
        when (count) {
            1 -> task1(currentPage)
        }

    }


    private fun readSetting() {

        val fileName = "task1.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        val settingList: List<String> = inputString.split("\n")

        val pageCountRead: List<String> = settingList[0].split("%№%","\r")
        val taskCountRead: List<String> = settingList[1].split("%№%","&?%","\r")
        val buttonRowsRead: List<String> = settingList[2].split("%№%","&?%","\r")
        val buttonInRowCountRead: List<String> = settingList[3].split("%№%","&?%","\r")
        val buttonTextRead: List<String> = settingList[4].split("%№%","&?%","\r")
        val elementCountRead: List<String> = settingList[5].split("%№%","&?%","\r")
        val pageContentRead: List<String> = settingList[6].split("%№%","&?%","\r")
        val editTextRead: List<String> = settingList[7].split("%№%","&?%","\r")
        val textTextRead: List<String> = settingList[8].split("%№%","&?%","\r")
        val rowTextTextRead: List<String> = settingList[9].split("%№%","&?%","\r")
        val rowTextSpanRead:List<String> = settingList[10].split("%№%","&?%","\r")
        val rightAnswerArrayRead: List<String> = settingList[11].split("%№%","&?%","\r")
        val taskTextRead: List<String> = settingList[12].split("%№%","&?%","\r")
        val codeTextRead: List<String> = settingList[13].split("%№%","&?%","\r")

        pageCountSet = pageCountRead[1].toInt() //количество страниц
        for(i in 1..taskCountRead.size-2) //количество тасков
            taskCount.add(taskCountRead[i].toInt())

        for(i in 1..buttonRowsRead.size-2) //количество кнопок
            buttonRows.add(buttonRowsRead[i].toInt())

        for(i in 1..buttonInRowCountRead.size-2) //количество кнопок в строке
            buttonInRowCount.add(buttonInRowCountRead[i].toInt())

        for(i in 1..buttonTextRead.size-2) //текст кнопок
            buttonText.add(buttonTextRead[i])

        for(i in 1..elementCountRead.size-2) //количество элементов на странице
            elementCount.add(elementCountRead[i].toInt())

        for(i in 1..pageContentRead.size-2) //контент на странице
            pageContent.add(pageContentRead[i])

        for(i in 1..editTextRead.size-2) //количество эдитов
            editText.add(editTextRead[i].toInt())

        for(i in 1..textTextRead.size-2) //Текст текстовых полей
            textText.add(textTextRead[i])

        for(i in 1..rowTextTextRead.size-2) //Текст текстовых полей в отдельной строке
            rowTextText.add(rowTextTextRead[i])

        for(i in 1..rowTextSpanRead.size-2) //спан строк
            rowTextSpan.add(rowTextSpanRead[i].toInt())

        for(i in 1..rightAnswerArrayRead.size-2) //Правильные ответы
            rightAnswers.add(rightAnswerArrayRead[i])

        for(i in 1..taskTextRead.size-2) //текст таска
            taskTextArray.add(taskTextRead[i])

        for(i in 1..codeTextRead.size-2) //текст кода
            codeTextArray.add(codeTextRead[i])

    }

    private fun task1(curPage: Int)
    {
        var count: Int = 0
        val taskText: TextView = findViewById(R.id.taskText)
        val enterCode: TextView = findViewById(R.id.enterCodeText)
        val butTable: TableLayout = findViewById(R.id.tableButton)
        val compileButton: ImageButton = findViewById(R.id.compileButton)
        reset(taskCount[curPage])


        taskText.text = taskTextArray[curPage]
        enterCode.text = codeTextArray[curPage]


        for(i in 0 until buttonRows[curPage])
        {
            buttonRowAdd(buttonInRowCount[buttonRowCounter+i])
            count++
            for(j in 0 until buttonInRowCount[buttonRowCounter+i])
            {
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), buttonText[buttonCounter+j], allTaskCount)
            }
            buttonCounter+=buttonInRowCount[buttonRowCounter+i]
        }
        buttonRowCounter+=count
        count=0

        for(i in 0 until elementCount[curPage])
        {

            when(pageContent[rowCounter+i])
            {
                "row"->{
                    rowAdd()
                }
                "edit"->{
                    newEdit(editText[editCounter])
                    editCounter++
                }
                "text"->{
                    newText(textText[textCounter])
                    textCounter++
                }
                "rowtext"->{
                    textRowAdd(rowTextText[rowTextCounter],rowTextSpan[rowTextCounter])
                    rowTextCounter++
                }
            }
            count++
        }

        rowCounter += count
        count = 0

        for(i in 0 until taskCount[curPage])
        {
            rightAnswerArray.add(rightAnswers[rightAnswerCounter+i])
            count++
        }
        rightAnswerCounter+=count



        rightPageCheck()
    }


    private fun reset(taskCount: Int)
    {
        rightAnswerArray.clear()
        curButtRow = -1//обнуляем количество строк кнопок
        curEditRow = -1 //обнуляем количество строк эдитов
        allTaskCount = taskCount
        butIndex = 0  //индекс кнопок
        editIndex = 0
        editInRow = 0

    }

    private fun rightPageCheck()
    {
        val butTable: TableLayout = findViewById(R.id.tableButton)
        val codeTable: TableLayout = findViewById(R.id.codeTable)

        if(rightPageArray[currentPage])
        {
            for(i in 0 until rightAnswerArray.size)
            {
                codeTable.findViewById<EditText>(editArray[i]).setText(rightAnswerArray[i])

                butTable.findViewById<Button>(buttonArray[i]).text=""
            }
        }

    }

    fun nextPage(view: View) //метод вызова следующей страницы
    {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        var tempEdit: EditText
        var isRight = true

        for(i in 0 until rightAnswerArray.size)
        {
            tempEdit = codeTable.findViewById(editArray[i])
            if(tempEdit.text.toString() != rightAnswerArray[i])
            {
                isRight = false
                tempEdit.backgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)))
            }
        }
        if(isRight) {
            if(currentPage!=pageCount-1) {
                rightPageArray[currentPage] = true
                currentPage++
                nextPageArray[currentPage] = true
                resetViews(view)
            }
            else
            {
                finish()
            }
        }

    }

    private fun buttonRowAdd(butCount: Int) //метод создания строки где будут кнопки
    {
        val butTable: TableLayout = findViewById(R.id.tableButton)      //инициализация таблицы
        val tempTab = TableRow(this)                            // инициализация динамической кнопки
        tempTab.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))  //цвет строки
        butTable.addView(tempTab)
        tempTab.id = View.generateViewId()
        curButtRow++        //увеличиваю счетчик строки
        rowsButArray.add(tempTab.id)
        newBut(butCount, curButtRow)

    }
    private fun newBut(butCount: Int, row: Int)
    {
        val butTable: TableLayout = findViewById(R.id.tableButton)
        for (i in 1..butCount) {
            buttonArray.add(buttAdd(butTable.findViewById(rowsButArray[row])))
        }
        butInRowArray.add(butCount)
    }

    private fun buttAdd(row: TableRow): Int {
        val button = Button(this)
        button.backgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)))
        button.background = ContextCompat.getDrawable(this, R.drawable.task_but_shape)
        button.setTextColor(ContextCompat.getColor(this, R.color.black))
        button.id = View.generateViewId()
        row.addView(button)
        return button.id
    }


    private fun textRowAdd(text: String, span: Int)
    {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        val tempTab = TableRow(this)
        val textV = TextView(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tempTab.background = ContextCompat.getDrawable(this, R.color.yellow)
        tempTab.id = View.generateViewId()
        params.span = span
        tempTab.layoutParams = params

        codeTable.addView(tempTab)

        params.setMargins(10, 10, 10, 10)

        textV.text = text
        textV.textSize = 16F

        textV.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textV.setTextColor(Color.BLACK)
        textV.layoutParams = params
        curTextRow++
        codeTable.findViewById<TableRow>(tempTab.id).addView(textV)

    }

    private fun rowAdd() {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        val tempTab = TableRow(this)
        tempTab.background = ContextCompat.getDrawable(this, R.color.yellow)
        codeTable.addView(tempTab)
        tempTab.id = View.generateViewId()
        curEditRow++
        editInRow = 0
        rowsArray.add(tempTab.id)
    }


    private fun newEdit(count: Int) {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        for (i in 1..count) {
            editArray.add(editAdd(codeTable.findViewById(rowsArray[curEditRow])))
            editInRow++
        }
        if(editInRowArray.size<=curEditRow)
            editInRowArray.add(editInRow)
        else
            editInRowArray.add(curEditRow, editInRow)

    }


    private fun editAdd(row: TableRow): Int {
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_NULL
        editText.isLongClickable = false
        editText.isFocusable = false
        editText.id = View.generateViewId()
        editIndex++
        row.addView(editText)
        return editText.id
    }

    private fun taskListener(edText: EditText, taskBut: Button) {

        taskBut.text = edText.text
        taskBut.isClickable = true
        edText.setText("")
        edText.backgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey)))
        edText.isClickable = false
    }

    private fun buttonListener(taskBut: Button, taskCount: Int) {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        var butRow = 0
        var countRow =0

        for (i in 0 until taskCount) {

            if (countRow == editInRowArray[butRow]) {
                butRow++
                countRow = 0
            }
            countRow++

            if (codeTable.findViewById<TableRow>(rowsArray[butRow]).findViewById<EditText>(editArray[i]) != null) {
                if (codeTable.findViewById<TableRow>(rowsArray[butRow]).findViewById<EditText>(editArray[i]).text.isEmpty()) {
                    codeTable.findViewById<TableRow>(rowsArray[butRow]).findViewById<EditText>(editArray[i]).setText(taskBut.text)
                    codeTable.findViewById<TableRow>(rowsArray[butRow]).findViewById<EditText>(editArray[i]).layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
                    val edText: EditText = findViewById(editArray[i])
                    taskBut.text = ""
                    taskBut.isClickable = false
                    edText.isClickable = true
                    edText.backgroundTintList = (ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey)))
                    edText.setOnClickListener {
                        taskListener(edText, taskBut)
                    }
                    break
                }
            }
        }
    }


    private fun buttonEdit(taskBut: Button, text: String, taskCount: Int) {
        val params = taskBut.layoutParams as TableRow.LayoutParams
        params.setMargins(5, 5, 5, 8)
        taskBut.layoutParams = params
        taskBut.text = text
        butIndex++
        taskBut.setOnClickListener {
            buttonListener(taskBut, taskCount)
        }


    }

    private fun newText(text: String) {
        val textV = TextView(this)
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        params.setMargins(10, 10, 10, 10)
        textV.text = text
        textV.textSize = 16F
        textV.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textV.setTextColor(Color.BLACK)
        textV.layoutParams = params
        codeTable.findViewById<TableRow>(rowsArray[curEditRow]).addView(textV)
    }

    fun resetViews(view: View) {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        val tableButton: TableLayout = findViewById(R.id.tableButton)
        editArray.clear()
        buttonArray.clear()
        rowsArray.clear()
        editInRowArray.clear()
        rowsButArray.clear()
        butInRowArray.clear()
        codeTable.removeAllViews()
        tableButton.removeAllViews()
        pageProceed()




    }

    private fun rowCompileAdd(text: String) {
        val codeTable: TableLayout = findViewById(R.id.codeTable)
        val tempTab = TableRow(this)
        val textV = TextView(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tempTab.background = ContextCompat.getDrawable(this, R.color.yellow_inactive)
        codeTable.addView(tempTab)
        params.setMargins(10, 10, 10, 10)
        textV.text = text
        tempTab.id = View.generateViewId()
        textV.textSize = 16F
        textV.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        textV.setTextColor(Color.BLACK)
        textV.layoutParams = params
        codeTable.findViewById<TableRow>(tempTab.id).addView(textV)
    }


    private fun compile(task: Int) {
        val compileButton: ImageButton = findViewById(R.id.compileButton)
        when(task)
        {
            1->
            {
                rowCompileAdd("Колицество цифр = 3")
                compileButton.isClickable = false

            }

        }



    }

}

/*
    private fun task1() {


        val taskText: TextView = findViewById(R.id.taskText)
        val enterCode: TextView = findViewById(R.id.enterCodeText)
        val butTable: TableLayout = findViewById(R.id.tableButton)
        val compileButton: ImageButton = findViewById(R.id.compileButton)

        when (currentPage) {
            1 -> {

                reset(6)

                taskText.text = resources.getString(R.string.task1_taskText1)
                enterCode.text = resources.getString(R.string.task1_codeText1)

                buttonRowAdd(3)
                buttonRowAdd(3)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "number", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "char", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "string", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "sNumber", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "count", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "int", allTaskCount)

                rowAdd()
                newEdit(2)
                newText(";")
                rowAdd()
                newEdit(2)
                newText(";")
                rowAdd()
                newEdit(2)
                newText(";")

                rightAnswerArray.add("char")
                rightAnswerArray.add("number")
                rightAnswerArray.add("int")
                rightAnswerArray.add("count")
                rightAnswerArray.add("string")
                rightAnswerArray.add("sNumber")

                rightPageCheck()

            }

            2 -> {
                reset(3)

                taskText.text = resources.getString(R.string.task1_taskText2)
                enterCode.text = resources.getString(R.string.task1_codeText2)


                buttonRowAdd(4)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "'3'", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "3", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "82347273312", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "0", allTaskCount)

                rowAdd()
                newText("char number = ")
                newEdit(1)
                newText(";")

                rowAdd()
                newText("int count = ")
                newEdit(1)
                newText(";")

                rowAdd()
                newText("string sNumber = ")
                newEdit(1)
                newText(";")

                rightAnswerArray.add("'3'")
                rightAnswerArray.add("0")
                rightAnswerArray.add("82347273312")

                rightPageCheck()

            }

            3 -> {
                reset(3)

                taskText.text = resources.getString(R.string.task1_taskText3)
                enterCode.text = resources.getString(R.string.task1_codeText3)



                buttonRowAdd(3)
                buttonRowAdd(3)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "-5", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "sNumber.Length", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "++", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "0", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "100", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "--", allTaskCount)



                rowAdd()
                newText("for(int i= ")
                newEdit(1)
                newText("i<")
                newEdit(1)
                newText("i")
                newEdit(1)
                newText(") {}")

                rightAnswerArray.add("0")
                rightAnswerArray.add("sNumber.Length")
                rightAnswerArray.add("++")

                rightPageCheck()

            }

            4 -> {
                reset(3)

                taskText.text = resources.getString(R.string.task1_taskText4)
                enterCode.text = resources.getString(R.string.task1_codeText4)



                buttonRowAdd(3)
                buttonRowAdd(3)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "count++;", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "count--;", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "sNumber[i]", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "number", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "sNumber[0]", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "count", allTaskCount)


               textRowAdd("for(int i=0; i<sNumber.Length; i++)",6)
               textRowAdd("{", 1)
                rowAdd()
                newText("")
                newText("if(")
                newEdit(1)
                newText("==")
                newEdit(1)
                newText(")")
                rowAdd()
                newText(" ")
                newText(" ")
                newEdit(1)
                textRowAdd("}", 1)

                rightAnswerArray.add("sNumber[i]")
                rightAnswerArray.add("number")
                rightAnswerArray.add("count++;")

                rightPageCheck()

            }
            5 -> {
                reset(1)

                taskText.text = resources.getString(R.string.task1_taskText5)
                enterCode.text = resources.getString(R.string.task1_codeText5)



                buttonRowAdd(3)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "number", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "sNumber", allTaskCount)
                buttonEdit(butTable.findViewById(buttonArray[butIndex]), "count", allTaskCount)



                rowAdd()
                newText("Console.WriteLine(Количество цифр = \"+" )
                newEdit(1)
                newText(");")



                rightAnswerArray.add("count")

                rightPageCheck()

            }
            6 -> {
                reset(0)

                taskText.text = resources.getString(R.string.task1_taskText6)
                enterCode.text = ""

                textRowAdd("char number = '3';",1)
                textRowAdd("string sNumber = \"82347273312\";",1)
                textRowAdd("int count = 0;",1)
                textRowAdd("for(int i=0; i<sNumber.Length; i++)",1)
                textRowAdd("{",1)
                textRowAdd("   if(sNumber[i]==number)",1)
                textRowAdd("       count++;",1)
                textRowAdd("}",1)
                textRowAdd(" Console.WriteLine(\"Количество цифр \" + count)",1)

                compileButton.visibility = View.VISIBLE
                compileButton.setOnClickListener{
                    compile(1)
                }



            }



        }


    }

 */