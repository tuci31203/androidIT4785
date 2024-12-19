package com.example.filemanager

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filemanager.databinding.ActivityTextFileBinding
import java.io.File

class TextFileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextFileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filePath = intent.getStringExtra("FILE_PATH")
        filePath?.let{
            val file = File(it)
            try{
                binding.textFileContent.text = file.readText()
            }catch (e:Exception){
                val message: String = "Unable to read file! ${e.localizedMessage}"
                binding.textFileContent.text = message
            }

        }
    }
}