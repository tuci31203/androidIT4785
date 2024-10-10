package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var currInput: String = ""
    var currOp: String? = null
    var op1: Int? = null
    var isPos: Boolean = true
    lateinit var res: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_linear)

        res = findViewById(R.id.res)

        for(i in 0..9){
            val buttonId = resources.getIdentifier("num$i", "id", packageName)
            findViewById<Button>(buttonId).setOnClickListener { appendNum(i.toString()) }
        }

        findViewById<Button>(R.id.plus).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.minus).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.multiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.divide).setOnClickListener { setOperator("/") }
        findViewById<Button>(R.id.equal).setOnClickListener { calc() }

        findViewById<Button>(R.id.sign).setOnClickListener { changeSign() }
        findViewById<Button>(R.id.clearBtn).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.clearEntry).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.backspace).setOnClickListener { backspace() }
    }

    fun backspace(){
        if(currInput.isNotEmpty()){
            currInput = currInput.dropLast(1)
            updateDisplay()
        }
    }

    fun clearEntry(){
        currInput = ""
        updateDisplay()
    }

    fun clearAll(){
        currInput = ""
        op1 = null
        currOp = null
        isPos = true
        updateDisplay()
    }

    fun updateDisplay(){
        res.text = currInput.ifEmpty { "0" }
    }

    fun appendNum(num: String){
        currInput += num
        updateDisplay()
    }

    fun setOperator(op: String){
        if(currInput.isNotEmpty()){
            if(op1 == null){
                op1 = currInput.toInt()
            }else{
                calc()
            }
            currOp = op
            currInput = ""
        }
    }

    fun calc(){
        if(op1 != null && currOp != null && currInput.isNotEmpty()){
            val op2 = currInput.toInt()
            val result = when (currOp){
                "+" -> op1!! + op2
                "-" -> op1!! - op2
                "*" -> op1!! * op2
                "/" -> op1!! / op2
                else -> return
            }
            currInput = result.toString()
            op1 = null
            currOp = null
            updateDisplay()
        }
    }
    fun changeSign(){
        isPos = !isPos
        if(currInput.isNotEmpty()){
            currInput = if(isPos) currInput.removePrefix("-") else "-$currInput"
        }
        updateDisplay()
    }
}