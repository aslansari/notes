package com.aslansari.notes.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import com.aslansari.notes.note.Note

class NoteListDiffCallback(val oldNoteList: List<Note>, val newNoteList: List<Note>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldNoteList.size
    }

    override fun getNewListSize(): Int {
        return newNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newNoteList[newItemPosition].id == oldNoteList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newNoteList[newItemPosition] == oldNoteList[oldItemPosition]
    }
}