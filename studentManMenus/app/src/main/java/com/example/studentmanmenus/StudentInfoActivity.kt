package com.example.studentmanmenus

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanmenus.models.StudentModel

class StudentInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)

        val editHoten = findViewById<EditText>(R.id.edit_hoten)
        val editMssv = findViewById<EditText>(R.id.edit_mssv)


        findViewById<Button>(R.id.button_ok).setOnClickListener {
            val hoten = editHoten.text.toString().trim()
            val mssv = editMssv.text.toString().trim()
            if(hoten.isNotEmpty() && mssv.isNotEmpty()){
                intent.putExtra("hoten", hoten)
                intent.putExtra("mssv", mssv)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "Hãy nhập họ tên và mssv!", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.button_cancel).setOnClickListener {
            finish()
        }

    }
}