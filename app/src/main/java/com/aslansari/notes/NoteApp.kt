package com.aslansari.notes

import android.app.Application
import com.aslansari.notes.note.NoteDatabase
import com.aslansari.notes.note.NoteRepository

class NoteApp: Application() {

    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.getNoteDAO()) }
}