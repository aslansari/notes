package com.aslansari.notes

import com.aslansari.notes.note.Note
import com.aslansari.notes.note.NoteDAO
import com.aslansari.notes.note.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class NoteViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteDAO: NoteDAO
    @Before
    fun setup() {

        noteDAO = mock {
            on { getNotes() } doReturn flow {  }
        }

        val noteRepository = NoteRepository(noteDAO)
        viewModel = NoteViewModel(mainCoroutineRule.testDispatcher, noteRepository)
    }

    @Test
    fun `Testing adding note`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        viewModel.addNote(getNote())
        viewModel.addNote(getNote())
        val testResults = mutableListOf<MutableList<Note>>()
        val job = launch {
            viewModel.noteFlow.toList(testResults.toMutableList())
        }
        verify(noteDAO) {
            2 * { insertNote(any())}
        }
        job.cancel()
    }

    @Test
    fun `Testing editing note`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        val note = getNote()
        viewModel.addNote(note)
        viewModel.editNote(note.copy(title = "newTitle"))
        val testResults = mutableListOf<MutableList<Note>>()
        val job = launch {
            viewModel.noteFlow.toList(testResults.toMutableList())
        }
        verify(noteDAO) {
            1 * { insertNote(any()) }
            1 * { updateNote(any()) }
        }
        job.cancel()
    }

    @Test
    fun `Testing deleting note`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        val note = getNote()
        viewModel.addNote(note)
        viewModel.deleteNote(note.id)
        val testResults = mutableListOf<MutableList<Note>>()
        val job = launch {
            viewModel.noteFlow.toList(testResults.toMutableList())
        }
        verify(noteDAO) {
            1 * { insertNote(any()) }
            1 * { deleteNote(any()) }
        }
        job.cancel()
    }

    fun getNote(): Note {
        return Note(
            title = "title",
            content = "content",
            imageUrl = "",
            createdAt = "",
            editedAt = "",
        )
    }

    /**
     * Although it looks deprecated it
     */
    class MainCoroutineRule(
        val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher() {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            Dispatchers.resetMain()
            testDispatcher.cleanupTestCoroutines()
        }
    }
}
