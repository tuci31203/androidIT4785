package com.example.studentmansql.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.studentmansql.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        val editHoten = view.findViewById<EditText>(R.id.edit_hoten)
        val editMssv = view.findViewById<EditText>(R.id.edit_mssv)

        val name = arguments?.getString("name")
        val id = arguments?.getString("id")
        val pos = arguments?.getInt("pos")
        val old_id = arguments?.getString("old_id")

        editHoten.setText(name)
        editMssv.setText(id)

        view.findViewById<Button>(R.id.button_cancel).setOnClickListener{
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val name = editHoten.text.toString().trim()
            val id = editMssv.text.toString().trim()
            if(name.isNotEmpty() && id.isNotEmpty()){
                val args = Bundle()
                args.putString("name", name)
                args.putString("id", id)
                args.putInt("pos", pos!!)
                args.putString("old_id", old_id)
                parentFragmentManager.setFragmentResult("edit", args)
                findNavController().navigateUp()
            }
        }

        return view
    }


}