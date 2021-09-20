package com.riyazuddin.noteit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val date: Long,
    val owner: String,
    val color: String,
    @Expose(deserialize = false, serialize = false)
    val isSynced: Boolean
)
