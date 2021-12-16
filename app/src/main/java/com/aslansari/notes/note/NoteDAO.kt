package com.aslansari.notes.note

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes WHERE id=:id")
    suspend fun deleteNote(id: String)

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>
}