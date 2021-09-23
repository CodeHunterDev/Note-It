package com.riyazuddin.noteit.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.riyazuddin.noteit.data.model.LocallyDeletedNote
import com.riyazuddin.noteit.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteByID(noteId: String)

    @Query("SELECT * FROM notes WHERE isSynced = 0")
    suspend fun getAllUnSyncedNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun observeNote(noteId: String): LiveData<Note>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNote(noteId: String): Note?

    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocallyDeletedNoteId(locallyDeletedNote: LocallyDeletedNote)

    @Query("DELETE FROM locally_deleted_note WHERE locallyDeletedNoteId = :locallyDeletedNoteId")
    suspend fun deletedLocallyDeletedNote(locallyDeletedNoteId: String)

    @Query("SELECT * FROM locally_deleted_note")
    suspend fun getAllLocallyDeleteNotes(): List<LocallyDeletedNote>
}