package com.aslansari.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.aslansari.notes.note.Note
import com.aslansari.notes.util.NoteInputValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteFragment: Fragment() {

    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val noteViewModel: NoteViewModel by activityViewModels(factoryProducer = {
        NoteViewModelFactory((activity?.application as NoteApp).repository)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_note, container, false)
        val etTitle = rootView.findViewById(R.id.etTitle) as TextInputEditText
        val etContent = rootView.findViewById(R.id.etContent) as TextInputEditText
        val etImageUrl = rootView.findViewById(R.id.etImageUrl) as TextInputEditText
        val buttonAddNote = rootView.findViewById(R.id.buttonAddNote) as MaterialButton
        val buttonDelete = rootView.findViewById(R.id.buttonDelete) as MaterialButton

        var isEditing = false
        val id = arguments?.get("id")
        var currentNote: Note? = null
        if (id != null) {
            currentNote = noteViewModel.noteFlow.value.last {
                it.id == id
            }
            etTitle.setText(currentNote.title)
            etContent.setText(currentNote.content)
            etImageUrl.setText(currentNote.imageUrl)
            isEditing = true
        }

        buttonDelete.visibility = if (isEditing) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        buttonAddNote.setText(
            if (isEditing) {
                R.string.edit
            } else {
                R.string.add
            }
        )

        fun addOrEdit() {
            val imageUrl = etImageUrl.text.toString()
            if (imageUrl.isNotEmpty() && !NoteInputValidator.validateURL(imageUrl)) {
                etImageUrl.error = "URL is not valid"
                return
            }
            if (isEditing) {
                currentNote?.let {
                    noteViewModel.editNote(
                        note = currentNote.copy(
                            title = etTitle.text.toString(),
                            content = etContent.text.toString(),
                            imageUrl = etImageUrl.text.toString(),
                            editedAt = simpleDateFormat.format(Calendar.getInstance().time)
                        )
                    )
                }
            } else {
                noteViewModel.addNote(
                    note = Note(
                        etTitle.text.toString(),
                        etContent.text.toString(),
                        etImageUrl.text.toString(),
                        simpleDateFormat.format(Calendar.getInstance().time),
                        "")
                )
            }
        }

        noteViewModel.viewModelScope.launch {
            val imageUrlText = MutableStateFlow("")

            etImageUrl.doOnTextChanged { text, _, _, _ ->
                imageUrlText.value = text.toString()
            }
            imageUrlText
                .debounce(1000)
                .flowOn(Dispatchers.Main)
                .collect {
                    if (it.isNotEmpty()) {
                        val isValid = NoteInputValidator.validateURL(it)
                        buttonAddNote.isEnabled = isValid
                        if (!isValid) {
                            etImageUrl.error = "URL is not valid"
                        }
                    } else {
                        buttonAddNote.isEnabled = true
                    }
                }
        }

        etContent.setOnEditorActionListener { _, actionId, _ ->
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                findNavController().navigateUp()
                addOrEdit()
            }
            false
        }

        buttonAddNote.setOnClickListener {
            findNavController().navigateUp()
            addOrEdit()
            hideKeyboard()
        }

        buttonDelete.setOnClickListener {
            findNavController().navigateUp()
            currentNote?.id?.let { id -> noteViewModel.deleteNote(id) }
        }

        return rootView
    }

    private fun hideKeyboard() {
        val focusedEditTextView = activity?.window?.currentFocus

        val inputMethodManager = (activity?.getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager

        if (inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(focusedEditTextView?.windowToken, 0)
        }
    }
}