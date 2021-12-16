package com.aslansari.notes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aslansari.notes.note.Note
import com.aslansari.notes.note.NoteDAO
import com.aslansari.notes.note.NoteDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteStorageTest {

    companion object {
        val note = Note(
            title = "title",
            content = "content",
            imageUrl = "imageUrl",
            createdAt = "11/12/21",
            editedAt = "12/12/21",
        )
    }

    private lateinit var noteDAO: NoteDAO
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDAO = db.getNoteDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeNoteAndReadInList() = runBlocking {
        val insertedNote = note.copy(title = "insertTest")
        noteDAO.insertNote(insertedNote)
        val notes = noteDAO.getNotes().first()
        assert(notes[0].title == insertedNote.title)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndUpdateNoteAndReadInList() = runBlocking {
        val insertedNote = note.copy(title = "insertTest")
        noteDAO.insertNote(insertedNote)
        val notes = noteDAO.getNotes().first()
        assert(notes[0].title == insertedNote.title)

        val updatedNote = insertedNote.copy(title = "updatedNote")
        noteDAO.updateNote(updatedNote)
        val updatedNotes = noteDAO.getNotes().first()
        assert(updatedNotes[0].title == updatedNote.title)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteNoteAndReadInList() = runBlocking {
        val insertedNote = note.copy(title = "insertTest")
        noteDAO.insertNote(insertedNote)
        val notes = noteDAO.getNotes().first()
        assert(notes[0].title == insertedNote.title)

        noteDAO.deleteNote(insertedNote.id)
        val updatedNotes = noteDAO.getNotes().first()
        assert(updatedNotes.isEmpty())
    }
}