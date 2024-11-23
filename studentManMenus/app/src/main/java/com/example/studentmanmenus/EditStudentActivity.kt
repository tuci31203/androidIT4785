package com.example.studentmanmenus

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)

        val editHoten = findViewById<EditText>(R.id.edit_hoten)
        val editMssv = findViewById<EditText>(R.id.edit_mssv)

        val pos = intent.getIntExtra("pos", 0)
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        editHoten.setText(name)
        editMssv.setText(id)


        findViewById<Button>(R.id.button_ok).setOnClickListener {
            val hoten = editHoten.text.toString().trim()
            val mssv = editMssv.text.toString().trim()
            if(hoten.isNotEmpty() && mssv.isNotEmpty()){
                intent.putExtra("hoten", hoten)
                intent.putExtra("mssv", mssv)
                intent.putExtra("pos", pos)
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