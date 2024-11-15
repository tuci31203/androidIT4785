package vn.edu.hust.studentman

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val students = mutableListOf(
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

    val studentAdapter = StudentAdapter(students)

    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_students)
    recyclerView.run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    studentAdapter.setRecyclerView(recyclerView)

    val addBtn = findViewById<Button>(R.id.btn_add_new)
    addBtn.setOnClickListener {
      val dialog = Dialog(this)
      dialog.setContentView(R.layout.layout_dialog)
      dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

      dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
        val editHoten = dialog.findViewById<EditText>(R.id.edit_hoten)
        val editMssv = dialog.findViewById<EditText>(R.id.edit_mssv)
        val hoten = editHoten.text.toString().trim()
        val mssv = editMssv.text.toString().trim()
        if(hoten.isNotEmpty() && mssv.isNotEmpty()){
          students.add(0, StudentModel(hoten, mssv))
          studentAdapter.notifyItemInserted(0)
          recyclerView.scrollToPosition(0)
          Log.v("TAG", "$hoten - $mssv")

          dialog.dismiss()
        }else{
          Toast.makeText(this, "Hãy nhập họ tên và mssv!", Toast.LENGTH_SHORT).show()
        }
      }

      dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
        dialog.dismiss()
      }

      dialog.show()
    }
  }

  private fun showDialog(){

  }

}