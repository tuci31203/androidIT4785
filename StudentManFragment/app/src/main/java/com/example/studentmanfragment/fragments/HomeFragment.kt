package com.example.studentmanfragment.fragments

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentmanfragment.R
import com.example.studentmanfragment.adapters.StudentAdapter
import com.example.studentmanfragment.controllers.DeleteStudentController
import com.example.studentmanfragment.models.StudentModel


class HomeFragment : Fragment() {

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
    private lateinit var studentList: ListView
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        studentList = view.findViewById<ListView>(R.id.studentList)
        studentAdapter = StudentAdapter(students)
        studentList.adapter = studentAdapter

        parentFragmentManager.setFragmentResultListener("add",this, {_,args ->
            val name = args.getString("name")
            val id = args.getString("id")
            if (name != null && id != null) {
                students.add(0, StudentModel(name, id))
                studentAdapter.notifyDataSetChanged()
                studentList.setSelection(0)
            }
        })

        parentFragmentManager.setFragmentResultListener("edit", this){_, args ->
            val name = args.getString("name")
            val id = args.getString("id")
            val pos = args.getInt("pos")
            if(name != null && id!= null ){
                students[pos] = StudentModel(name, id)
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
        val pos = (item.menuInfo as AdapterContextMenuInfo).position
        when (item.itemId) {
            R.id.action_edit -> {
                val args = Bundle()
                args.putString("name", students[pos].name)
                args.putString("id", students[pos].id)
                args.putInt("pos", pos)
                findNavController().navigate(R.id.action_homeFragment_to_editFragment, args)

                true
//                EditStudentController(students, pos, studentAdapter, this).editStudent()
            }
            R.id.action_delete -> {
                DeleteStudentController(students, studentAdapter, requireContext(), pos,studentList).deleteStudent()
                true
            }
        }
        return super.onContextItemSelected(item)
    }

}