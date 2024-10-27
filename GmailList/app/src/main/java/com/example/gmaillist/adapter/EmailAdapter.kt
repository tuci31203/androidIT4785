package com.example.gmaillist.adapter

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gmaillist.databinding.ItemEmailBinding
import com.example.gmaillist.model.EmailItem
import kotlin.math.absoluteValue

class EmailAdapter : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {
    private var emails = listOf<EmailItem>()
    private val colorCache = mutableMapOf<String, Int>()
    private val backgroundColors = listOf(
        Color.parseColor("#F44336"), // Red
        Color.parseColor("#E91E63"), // Pink
        Color.parseColor("#9C27B0"), // Purple
        Color.parseColor("#673AB7"), // Deep Purple
        Color.parseColor("#3F51B5"), // Indigo
        Color.parseColor("#2196F3"), // Blue
        Color.parseColor("#03A9F4"), // Light Blue
        Color.parseColor("#00BCD4")  // Cyan
    )

    class EmailViewHolder(private val binding: ItemEmailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(email: EmailItem, backgroundColor: Int) {
            binding.apply {
                senderInitial.text = email.senderName.first().toString()
                senderInitial.background = createCircularDrawable(backgroundColor)
                senderName.text = email.senderName
                emailSubject.text = email.subject
                emailPreview.text = email.preview
                emailTimestamp.text = email.timestamp
            }
        }

        private fun createCircularDrawable(color: Int): ShapeDrawable {
            return ShapeDrawable(OvalShape()).apply {
                paint.color = color
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val binding = ItemEmailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]
        val backgroundColor = colorCache.getOrPut(email.senderName) {
            backgroundColors[email.senderName.hashCode().absoluteValue % backgroundColors.size]
        }
        holder.bind(email, backgroundColor)
    }

    override fun getItemCount() = emails.size

    fun submitList(newEmails: List<EmailItem>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = emails.size
            override fun getNewListSize() = newEmails.size
            override fun areItemsTheSame(oldPos: Int, newPos: Int) =
                emails[oldPos].senderName == newEmails[newPos].senderName
            override fun areContentsTheSame(oldPos: Int, newPos: Int) =
                emails[oldPos] == newEmails[newPos]
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        emails = newEmails
        diffResult.dispatchUpdatesTo(this)
    }
}