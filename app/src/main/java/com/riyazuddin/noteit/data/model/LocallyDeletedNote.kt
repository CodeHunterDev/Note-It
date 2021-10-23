package com.riyazuddin.noteit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locally_deleted_note")
data class LocallyDeletedNote(
    @PrimaryKey(autoGenerate = false)
    val locallyDeletedNoteId: String,
)
