package com.example.gmaillist.model

data class EmailItem(
    val senderName: String,
    val subject: String,
    val preview: String,
    val timestamp: String
)