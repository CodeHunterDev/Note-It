package com.riyazuddin.noteit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.riyazuddin.noteit.presentation.ui.theme.*
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val owner: String = "613b7045df47b27efb18626d",
    val color: Int,
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false
) {
    companion object {
        val noteColors = listOf(
            RedOrange, RedPink,
            BabyBlue, Violet, LightGreen
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)
