package com.example.studentmansql.controllers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.studentmansql.adapters.StudentAdapter
import com.example.studentmansql.models.StudentModel
import com.google.android.material.snackbar.Snackbar

class DeleteStudentController(val students: MutableList<StudentModel>,
                              val studentAdapter: StudentAdapter,
                              val context: Context,
                              val position: Int,
                              val studentList: ListView,
                              val db: SQLiteDatabase,
                              val loadStudents: () -> Unit
) {
    fun deleteStudent(){
        val student = students[position]
        AlertDialog.Builder(context)
            .setTitle("Xóa sinh viên")
            .setMessage("Bạn có chắc muốn xóa sinh viên ${student.name} ?")
            .setPositiveButton("OK",{ dialog, _ ->
                db.beginTransaction()
                try{
                    val query = """DELETE FROM students WHERE studentId = ${student.id}"""
                    db.execSQL(query)
                    db.setTransactionSuccessful()
                    loadStudents()
                    studentAdapter.notifyDataSetChanged()
                }catch(e: Exception){
                    e.printStackTrace()
                    Toast.makeText(context, "DELETE ERROR", Toast.LENGTH_SHORT).show()
                }finally {
                    db.endTransaction()
                }
                studentAdapter.notifyDataSetChanged()
                Snackbar.make(
                    studentList,
                    "Đã xóa ${student.name} - ${student.id}",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Undo"){
                        db.beginTransaction()
                        try{
                            val query = """insert into students(studentId, studentName) values(?,?)"""
                            db.execSQL(query, arrayOf(student.id, student.name))
                            db.setTransactionSuccessful()
                            loadStudents()
                            studentAdapter.notifyDataSetChanged()
                        }catch(e: Exception){
                            e.printStackTrace()
                            Toast.makeText(context, "CANNOT UNDO", Toast.LENGTH_SHORT).show()
                        }finally {
                            db.endTransaction()
                        }
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