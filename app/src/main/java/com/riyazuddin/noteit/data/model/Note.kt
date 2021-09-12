package com.riyazuddin.noteit.data.model

data class Note(
    val id: String,
    val title: String,
    val subTitle: String,
    val content: String,
    val date: Long,
    val owners: String,
    val color: String
)
