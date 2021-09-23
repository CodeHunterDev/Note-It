package com.riyazuddin.noteit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.riyazuddin.noteit.presentation.ui.theme.*
import java.util.*

@Entity(tableName = "locally_deleted_note")
data class LocallyDeletedNote(
    @PrimaryKey(autoGenerate = false)
    val locallyDeletedNoteId: String,
)
