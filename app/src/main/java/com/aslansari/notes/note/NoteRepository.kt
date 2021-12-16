package com.aslansari.notes.note

open class NoteRepository(private val noteDAO: NoteDAO) {

    val noteFlow = noteDAO.getNotes()

    suspend fun insertNote(note: Note) {
        noteDAO.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDAO.updateNote(note)
    }

    suspend fun deleteNote(id: String) {
        noteDAO.deleteNote(id)
    }
}