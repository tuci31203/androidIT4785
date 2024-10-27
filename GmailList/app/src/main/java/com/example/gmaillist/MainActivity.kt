package com.example.gmaillist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gmaillist.adapter.EmailAdapter
import com.example.gmaillist.databinding.ActivityMainBinding
import com.example.gmaillist.model.EmailItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val emailAdapter = EmailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupFab()
        loadEmails()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = emailAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.menuButton.setOnClickListener {
        }

        binding.searchButton.setOnClickListener {
        }
    }


    private fun setupFab() {
        binding.fab.setOnClickListener {
        }
    }

    private fun loadEmails() {
        // Sample data
        val emails = listOf(
            EmailItem(
                "John Doe",
                "Meeting Tomorrow",
                "Hi, let's discuss the project details tomorrow at 10 AM.",
                "10:30 AM"
            ),
            EmailItem(
                "Alice Smith",
                "Project Update",
                "Here's the latest update on the ongoing development.",
                "9:15 AM"
            ),
            EmailItem(
                "Edurila.com",
                "$19 Only (First 10 spots) - Bestselling...",
                "Are you looking to Learn Web Designin...",
                "12:34 PM"
            ),
            EmailItem(
                "Chris Abad",
                "Help make Campaign Monitor better",
                "Let us know your thoughts! No Images...",
                "11:22 AM"
            ),
            EmailItem(
                "Tuto.com",
                "8h de formation gratuite et les nouvea...",
                "Photoshop, SEO, Blender, CSS, WordPre...",
                "11:04 AM"
            ),EmailItem(
                "Alice Smith",
                "Project Update",
                "Here's the latest update on the ongoing development.",
                "9:15 AM"
            ),
            EmailItem(
                "Edurila.com",
                "$19 Only (First 10 spots) - Bestselling...",
                "Are you looking to Learn Web Designin...",
                "12:34 PM"
            ),
            EmailItem(
                "Chris Abad",
                "Help make Campaign Monitor better",
                "Let us know your thoughts! No Images...",
                "11:22 AM"
            ),
            EmailItem(
                "Marble",
                "8h de formation gratuite et les nouvea...",
                "Photoshop, SEO, Blender, CSS, WordPre...",
                "11:04 AM"
            ),EmailItem(
                "Alice Smith",
                "Project Update",
                "Here's the latest update on the ongoing development.",
                "9:15 AM"
            ),
            EmailItem(
                "Edurila.com",
                "$19 Only (First 10 spots) - Bestselling...",
                "Are you looking to Learn Web Designin...",
                "12:34 PM"
            ),
            EmailItem(
                "Chris Abad",
                "Help make Campaign Monitor better",
                "Let us know your thoughts! No Images...",
                "11:22 AM"
            ),
            EmailItem(
                "Tuto.com",
                "8h de formation gratuite et les nouvea...",
                "Photoshop, SEO, Blender, CSS, WordPre...",
                "11:04 AM"
            )
        )
        emailAdapter.submitList(emails)
    }
}