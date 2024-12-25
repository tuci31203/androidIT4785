package com.example.studentmanroom.fragments

import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.studentmanroom.DAO.StudentDao
import com.example.studentmanroom.R
import com.example.studentmanroom.adapters.StudentAdapter
import com.example.studentmanroom.controllers.DeleteStudentController
import com.example.studentmanroom.database.StudentDatabase
import com.example.studentmanroom.models.StudentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private val students = mutableListOf<StudentModel>()
    private lateinit var studentList: ListView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        studentDao = StudentDatabase.getInstance(requireContext()).studentDao()
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
                lifecycleScope.launch(Dispatchers.IO){
                    val res = studentDao.insertStudent(StudentModel(
                        name = name,
                        id = id
                    ))
                    loadStudents()
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), "Đã thêm sinh viên [$res]",Toast.LENGTH_SHORT)
                            .show()
                        studentAdapter.notifyDataSetChanged()
                        studentList.setSelection(0)
                    }
                }

            }
        })

        parentFragmentManager.setFragmentResultListener("edit", this){_, args ->
            val name = args.getString("name")
            val id = args.getString("id")
            val _id = args.getInt("_id")
            val pos = args.getInt("pos")
            if(name != null && id!= null && _id != null ){
                lifecycleScope.launch(Dispatchers.IO){
                    val res = studentDao.updateStudent(
                        StudentModel(
                        _id = _id,
                        name = name,
                        id = id
                    )
                    )
                    loadStudents()
                    withContext(Dispatchers.Main){
                        Toast.makeText(
                            requireContext(),
                            "Đã cập nhật sinh viên [$res]",
                            Toast.LENGTH_SHORT
                        ).show()
                        studentAdapter.notifyDataSetChanged()
                    }
                }
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
                args.putInt("_id", students[pos]._id)
                args.putInt("pos", pos)
                findNavController().navigate(R.id.action_homeFragment_to_editFragment, args)
                true
//                EditStudentController(students, pos, studentAdapter, this).editStudent()
            }
            R.id.action_delete -> {
                DeleteStudentController(
                    students, studentAdapter,
                    requireContext(),
                    pos,
                    studentList,
                    studentDao,
                    this.lifecycleScope,
                    { loadStudents() }
                ).deleteStudent()
                true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun loadStudents(){
        students.clear()
        lifecycleScope.launch(Dispatchers.IO){
            val allStudents = studentDao.getAllStudents()
            for(student in allStudents){
                students.add(student)
            }
        }
    }

}