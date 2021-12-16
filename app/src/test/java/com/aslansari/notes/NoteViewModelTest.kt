package com.aslansari.notes

import com.aslansari.notes.note.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NoteViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: NoteViewModel
    @Before
    fun setup() {
        viewModel = NoteViewModel(mainCoroutineRule.testDispatcher)
    }

    @Test
    fun `Testing adding note`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        viewModel.addNote(getNote())
        viewModel.addNote(getNote())
        val testResults = mutableListOf<MutableList<Note>>()
        val job = launch {
            viewModel.noteFlow.toList(testResults)
        }

        assert(2 == testResults.first().size)
        assert("content" == testResults.first()[0].content)
        job.cancel()
    }

    @Test
    fun `Testing editing note`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        val note = getNote()
        viewModel.addNote(note)
        viewModel.editNote(note.copy(title = "newTitle"))
        val testResults = mutableListOf<MutableList<Note>>()
        val job = launch {
            viewModel.noteFlow.toList(testResults)
        }

        assert(1 == testResults.first().size)
        assert("newTitle" == testResults.first()[0].title)
        job.cancel()
    }

    @Test
    fun `Testing deleting note`(): Unit = mainCoroutineRule.testDispatcher.runBlockingTest {
        val note = getNote()
        viewModel.addNote(note)
        viewModel.deleteNote(note.id)
        val testResults = mutableListOf<MutableList<Note>>()
        val job = launch {
            viewModel.noteFlow.toList(testResults)
        }

        assert(0 == testResults.first().size)
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
