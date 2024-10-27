package com.example.currencycalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var inputFrom: EditText
    private lateinit var inputTo: EditText
    private lateinit var currFrom: Spinner
    private lateinit var currTo: Spinner

    private var isUpdatingFrom: Boolean = false
    private var isUpdatingTo: Boolean = false

    private val exchange = mapOf(
        "USD" to 1.0,
        "VND" to 25369.96,
        "EUR" to 0.93,
        "BATH" to 33.66,
        "JPY" to 152.28
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputFrom = findViewById<EditText>(R.id.from)
        inputTo = findViewById<EditText>(R.id.to)
        currFrom = findViewById<Spinner>(R.id.spinnerFrom)
        currTo = findViewById<Spinner>(R.id.spinnerTo)

        setSpinners()
        setEditTexs()

    }

    private fun setSpinners(){
        val currencies = exchange.keys.toList()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            currencies
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        currFrom.adapter = adapter
        currTo.adapter = adapter
        currFrom.setSelection(currencies.indexOf("USD"))
        currTo.setSelection(currencies.indexOf("VND"))

//        val spinnerListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                updateConversion()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }
        currFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(inputFrom.text.isNotEmpty()){
                    updateConversion()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        currTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(inputTo.text.isNotEmpty()){
                    updateReverseConversion()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun setEditTexs(){
        inputFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(!isUpdatingTo && !isUpdatingFrom){
                    isUpdatingFrom = true
                    updateConversion()
                    isUpdatingFrom = false
                }
            }
        })
        inputTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(!isUpdatingFrom && !isUpdatingTo){
                    isUpdatingTo = true
                    updateReverseConversion()
                    isUpdatingTo = false
                }
            }
        })
    }

    private fun updateConversion(){
        val fromCurr = currFrom.selectedItem as String
        val toCurr = currTo.selectedItem as String

        val fromRate = exchange[fromCurr] ?: 1.0
        val toRate = exchange[toCurr] ?: 1.0

        val inputText = inputFrom.text.toString()
        if(inputText.isNotEmpty()){
            try{
                val inputAmount = inputText.toDouble()
                val res = inputAmount * (toRate / fromRate)
                inputTo.setText(String.format(Locale.US,"%.2f", res))
            }catch(e: NumberFormatException){
                inputTo.setText("")
            }
        }
        else{
            inputTo.setText("")
        }
    }

    private fun updateReverseConversion(){
        val fromCurr = currFrom.selectedItem as String
        val toCurr = currTo.selectedItem as String

        val fromRate = exchange[fromCurr] ?: 1.0
        val toRate = exchange[toCurr] ?: 1.0

        val inputText = inputTo.text.toString()
        if(inputText.isNotEmpty()){
            try{
                val inputAmount = inputText.toDouble()
                val res = inputAmount * (fromRate / toRate)
                inputFrom.setText(String.format(Locale.US,"%.2f", res))
            }catch(e: NumberFormatException){
                inputFrom.setText("")
            }
        }
        else{
            inputFrom.setText("")
        }
    }
}