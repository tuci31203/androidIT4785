package com.example.filemanager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.adapters.FileAdapter
import com.example.filemanager.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fileAdapter: FileAdapter
    private var currentPath: File = Environment.getExternalStorageDirectory()
    private lateinit var fileRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fileRecyclerView = binding.fileRecyclerView
        fileRecyclerView.layoutManager = LinearLayoutManager(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        loadFileList(currentPath)
    }

    private fun loadFileList(directory: File) {
        val files = directory.listFiles() ?: arrayOf()
        fileAdapter = FileAdapter(files.toList()) { file ->
            if (file.isDirectory) {
                currentPath = file
                loadFileList(file)
            } else {
                if (file.extension.lowercase() in listOf("txt", "log", "json", "xml", "csv")) {
                    openTextFile(file)
                } else {
                    Toast.makeText(this, "Unsupported file type", Toast.LENGTH_SHORT).show()
                }
            }
        }
        fileRecyclerView.adapter = fileAdapter
    }

    private fun openTextFile(file: File) {
        val intent = Intent(this, TextFileActivity::class.java).apply {
            putExtra("FILE_PATH", file.absolutePath)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        if(currentPath == Environment.getExternalStorageDirectory()){
            return
        }
        val parentFile = currentPath.parentFile
        if (parentFile != null) {
            currentPath = parentFile
            loadFileList(currentPath)
        } else {
            super.onBackPressed()
            return
        }
    }
}