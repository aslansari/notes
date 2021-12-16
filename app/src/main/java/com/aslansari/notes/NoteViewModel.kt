package com.aslansari.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aslansari.notes.note.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val dispatcher: CoroutineDispatcher): ViewModel() {

    // used by lifecycle
    constructor() : this(Dispatchers.IO)

    // TODO: 12/5/2021 add repository for persistence
    val _noteFlow = MutableStateFlow(mutableListOf<Note>())
    val noteFlow: StateFlow<MutableList<Note>> = _noteFlow

    fun addNote(note: Note) = viewModelScope.launch(dispatcher) {
        _noteFlow.value.add(note)
    }

    fun editNote(note: Note) = viewModelScope.launch(dispatcher) {
        val index = _noteFlow.value.indexOfFirst {
            it.id == note.id
        }
        if (index > -1) {
            _noteFlow.value[index] = note
        }
    }

    fun deleteNote(id: String) = viewModelScope.launch(dispatcher) {
        val note = _noteFlow.value.first {
            it.id == id
        }
        _noteFlow.value.remove(note)
    }
}