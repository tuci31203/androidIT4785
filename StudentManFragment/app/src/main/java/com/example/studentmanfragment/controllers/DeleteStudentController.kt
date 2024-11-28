package com.example.studentmanfragment.controllers

import android.content.Context
import android.widget.ListView
import com.example.studentmanfragment.adapters.StudentAdapter
import com.example.studentmanfragment.models.StudentModel
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog

class DeleteStudentController(val students: MutableList<StudentModel>,
                              val studentAdapter: StudentAdapter,
                              val context: Context,
                              val position: Int,
                              val studentList: ListView) {
    fun deleteStudent(){
        val student = students[position]
        AlertDialog.Builder(context)
            .setTitle("Xóa sinh viên")
            .setMessage("Bạn có chắc muốn xóa sinh viên ${student.name} ?")
            .setPositiveButton("OK",{ dialog, _ ->
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                Snackbar.make(
                    studentList,
                    "Đã xóa ${student.name} - ${student.id}",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Undo"){
                        students.add(position, student)
                        studentAdapter.notifyDataSetChanged()
                        if(position == 0 || position == students.size-1){
                            studentList.setSelection(position)
                        }
                    }.show()
            })
            .setNegativeButton("Hủy", null)
            .show()
    }
}