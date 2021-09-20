package com.riyazuddin.noteit.data.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val date: Long,
    val owner: String,
    val color: String
)
