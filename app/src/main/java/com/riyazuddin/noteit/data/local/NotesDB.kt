package com.riyazuddin.noteit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riyazuddin.noteit.data.model.LocallyDeletedNote
import com.riyazuddin.noteit.data.model.Note

@Database(
    entities = [Note::class, LocallyDeletedNote::class],
    version = 1
)
abstract class NotesDB : RoomDatabase() {

    abstract fun notedDao(): NoteDao
}