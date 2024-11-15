package vn.edu.hust.studentman

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>)
  : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var recyclerView: RecyclerView? = null
  fun setRecyclerView(rv: RecyclerView){
    this.recyclerView = rv
  }

  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageEdit.setOnClickListener{
      val dialog = Dialog(holder.itemView.context)
      dialog.setContentView(R.layout.layout_dialog)
      dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

      val editHoten = dialog.findViewById<EditText>(R.id.edit_hoten)
      val editMssv = dialog.findViewById<EditText>(R.id.edit_mssv)

      editHoten.setText(student.studentName)
      editMssv.setText(student.studentId)

      dialog.findViewById<Button>(R.id.button_ok).setOnClickListener{
        val newHoten = editHoten.text.toString().trim()
        val newMssv = editMssv.text.toString().trim()

        if(newHoten.isNotEmpty() && newMssv.isNotEmpty()){
          students[position] = StudentModel(newHoten, newMssv)
          notifyItemChanged(position)
          dialog.dismiss()
        }else{
          Toast.makeText(holder.itemView.context, "Hãy nhập họ tên và mssv", Toast.LENGTH_SHORT).show()
        }
      }

      dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
        dialog.dismiss()
      }
      dialog.show()
    }

    holder.imageRemove.setOnClickListener{
      AlertDialog.Builder(holder.itemView.context)
        .setTitle("Xóa sinh viên")
        .setMessage("Bạn có chắc muốn xóa sinh viên ${student.studentName} ?")
        .setPositiveButton("OK",{ dialog, _ ->
          students.removeAt(position)
          notifyItemRemoved(position)
          notifyItemRangeChanged(position, students.size)
          Snackbar.make(
            holder.itemView,
            "Đã xóa ${student.studentName} - ${student.studentId}",
            Snackbar.LENGTH_LONG
          )
            .setAction("Undo"){
              students.add(position, student)
              notifyItemInserted(position)
              notifyItemRangeChanged(position, students.size)
              if(position == 0 || position == students.size-1){
                recyclerView?.scrollToPosition(position)
              }
            }.show()
        })
        .setNegativeButton("Hủy", null)
        .show()
    }
  }

}