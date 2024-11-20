package com.example.studentmanmenus.controllers

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.studentmanmenus.R
import com.example.studentmanmenus.adapters.StudentAdapter
import com.example.studentmanmenus.models.StudentModel
import android.widget.Toast

class EditStudentController(val students: MutableList<StudentModel>, val position: Int,  val studentAdapter: StudentAdapter, val context: Context) {

    fun editStudent(){
        val student = students[position]
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_dialog)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val editHoten = dialog.findViewById<EditText>(R.id.edit_hoten)
        val editMssv = dialog.findViewById<EditText>(R.id.edit_mssv)

        editHoten.setText(student.name)
        editMssv.setText(student.id)

        dialog.findViewById<Button>(R.id.button_ok).setOnClickListener{
            val newHoten = editHoten.text.toString().trim()
            val newMssv = editMssv.text.toString().trim()

            if(newHoten.isNotEmpty() && newMssv.isNotEmpty()){
                students[position] = StudentModel(newHoten, newMssv)
                studentAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(context, "Hãy nhập họ tên và mssv", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}