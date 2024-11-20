package com.example.studentmanmenus.controllers

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.studentmanmenus.R
import com.example.studentmanmenus.adapters.StudentAdapter
import com.example.studentmanmenus.models.StudentModel

class AddStudentController(val students: MutableList<StudentModel>,
                           val studentAdapter: StudentAdapter,
                           val studentList:ListView,
                           val context: Context) {
    fun addStudent(){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_dialog)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val editHoten = dialog.findViewById<EditText>(R.id.edit_hoten)
            val editMssv = dialog.findViewById<EditText>(R.id.edit_mssv)
            val hoten = editHoten.text.toString().trim()
            val mssv = editMssv.text.toString().trim()
            if(hoten.isNotEmpty() && mssv.isNotEmpty()){
                students.add(0, StudentModel(hoten, mssv))
                studentAdapter.notifyDataSetChanged()
                studentList.setSelection(0)
                Log.v("TAG", "$hoten - $mssv")

                dialog.dismiss()
            }else{
                Toast.makeText(context, "Hãy nhập họ tên và mssv!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}