package com.aslansari.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aslansari.notes.note.Note
import com.aslansari.notes.note.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(private val dispatcher: CoroutineDispatcher, private val noteRepository: NoteRepository): ViewModel() {

    val noteFlow: StateFlow<List<Note>> = noteRepository.noteFlow.stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    fun addNote(note: Note) = viewModelScope.launch(dispatcher) {
        noteRepository.insertNote(note)
    }

    fun editNote(note: Note) = viewModelScope.launch(dispatcher) {
        noteRepository.updateNote(note)
    }

    fun deleteNote(id: String) = viewModelScope.launch(dispatcher) {
        noteRepository.deleteNote(id)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(Dispatchers.IO, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}