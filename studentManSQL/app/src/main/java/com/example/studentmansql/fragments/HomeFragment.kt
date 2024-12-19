package com.example.studentmansql.fragments

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.studentmansql.R
import com.example.studentmansql.adapters.StudentAdapter
import com.example.studentmansql.controllers.DeleteStudentController
import com.example.studentmansql.models.StudentModel

class HomeFragment : Fragment() {

    private val students = mutableListOf<StudentModel>()
    private lateinit var studentList: ListView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        db = SQLiteDatabase.openDatabase(requireContext().filesDir.path + "/mydb", null,
            SQLiteDatabase.CREATE_IF_NECESSARY)
        val createTable = """
            CREATE TABLE IF NOT EXISTS students(
            studentId TEXT PRIMARY KEY,
            studentName TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        studentList = view.findViewById<ListView>(R.id.studentList)
        loadStudents()
        studentAdapter = StudentAdapter(students)
        studentList.adapter = studentAdapter

        parentFragmentManager.setFragmentResultListener("add",this, {_,args ->
            val name = args.getString("name")
            val id = args.getString("id")
            if (name != null && id != null) {
                db.beginTransaction()
                try{
                    val query = "INSERT OR REPLACE INTO students (studentId, studentName) VALUES (?, ?)"
                    db.execSQL(query, arrayOf(id, name))
                    db.setTransactionSuccessful()
                    loadStudents()
                    studentAdapter.notifyDataSetChanged()
                    studentList.setSelection(0)
                }catch(e: Exception){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Database Error!", Toast.LENGTH_SHORT).show()
                }finally{
                    db.endTransaction()
                }
                studentAdapter.notifyDataSetChanged()
                studentList.setSelection(0)
            }
        })

        parentFragmentManager.setFragmentResultListener("edit", this){_, args ->
            val name = args.getString("name")
            val id = args.getString("id")
            val old_id = args.getString("old_id")
            val pos = args.getInt("pos")
            if(name != null && id!= null && old_id != null ){
                db.beginTransaction()
                try{
                    val query = """
                        update students
                        set studentName = '$name', studentId='$id'
                        where studentId = '$old_id'
                    """.trimIndent()
                    db.execSQL(query)
                    db.setTransactionSuccessful()
                    loadStudents()
                    studentAdapter.notifyDataSetChanged()
                }catch(e: Exception){
                    Toast.makeText(requireContext(), "Database Error", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }finally {
                    db.endTransaction()
                }
                studentAdapter.notifyDataSetChanged()
            }
        }

        registerForContextMenu(studentList)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)
                true
//                AddStudentController(students, studentAdapter, studentList, this).addStudent()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        when (item.itemId) {
            R.id.action_edit -> {
                val args = Bundle()
                args.putString("name", students[pos].name)
                args.putString("id", students[pos].id)
                args.putString("old_id", students[pos].id)
                args.putInt("pos", pos)
                findNavController().navigate(R.id.action_homeFragment_to_editFragment, args)
                true
//                EditStudentController(students, pos, studentAdapter, this).editStudent()
            }
            R.id.action_delete -> {
                DeleteStudentController(students, studentAdapter, requireContext(), pos,studentList, db,
                    { loadStudents() }
                ).deleteStudent()
                true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun loadStudents(){
        students.clear()
        val cs = db.query(
            "students",
            arrayOf("studentId", "studentName"),
            null,
            null,
            null,
            null,
            null)
        cs.moveToFirst()
        if (cs.moveToFirst()) {
            do {
                val id = cs.getString(0)
                val name = cs.getString(1)
                students.add(StudentModel(name, id))
                Log.v("TAG", "$id - $name")
            } while (cs.moveToNext())
        }
        cs.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::db.isInitialized && db.isOpen){
            db.close()
        }
    }

}