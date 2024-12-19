package com.example.filemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.R
import java.io.File

class FileAdapter (
    private val files: List<File>,
    private val onItemClick: (File) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.file_name_text_view)
        val fileTypeTextView: TextView = itemView.findViewById(R.id.file_type_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.fileNameTextView.text = file.name

        val fileType = when {
            file.isDirectory -> "Folder"
            file.extension.isNotEmpty() -> file.extension.uppercase() + " File"
            else -> "File"
        }
        holder.fileTypeTextView.text = fileType

        holder.itemView.setOnClickListener {
            onItemClick(file)
        }
    }

    override fun getItemCount() = files.size
}