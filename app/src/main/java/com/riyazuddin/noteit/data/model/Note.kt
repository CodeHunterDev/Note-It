package com.riyazuddin.noteit.data.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String>,
    val color: String
)
