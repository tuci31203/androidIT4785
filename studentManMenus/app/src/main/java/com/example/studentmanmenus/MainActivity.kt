package com.example.studentmanmenus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanmenus.adapters.StudentAdapter
import com.example.studentmanmenus.controllers.DeleteStudentController
import com.example.studentmanmenus.models.StudentModel

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: ListView
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val name = result.data?.getStringExtra("hoten")
            val id = result.data?.getStringExtra("mssv")
            if (name != null && id != null) {
                students.add(0, StudentModel(name, id))
                studentAdapter.notifyDataSetChanged()
                studentList.setSelection(0)
            }
        }
    }
    private val editStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val name = result.data?.getStringExtra("hoten")
            val id = result.data?.getStringExtra("mssv")
            val pos = result.data?.getIntExtra("pos", 0)
            if (name != null && id != null && pos != null) {
                students[pos] = StudentModel(name, id)
                studentAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students)
        studentList = findViewById(R.id.studentList)
        studentList.adapter = studentAdapter
        registerForContextMenu(studentList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, StudentInfoActivity::class.java)
                addStudentLauncher.launch(intent)
//                AddStudentController(students, studentAdapter, studentList, this).addStudent()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterContextMenuInfo).position
        when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("name", students[pos].name)
                intent.putExtra("id", students[pos].id)
                intent.putExtra("pos", pos)
                editStudentLauncher.launch(intent)

//                EditStudentController(students, pos, studentAdapter, this).editStudent()
            }
            R.id.action_delete -> {
                DeleteStudentController(students, studentAdapter, this, pos,studentList).deleteStudent()
            }
        }
        return super.onContextItemSelected(item)
        }
}