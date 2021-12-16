package com.aslansari.notes.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.notes.R
import com.aslansari.notes.note.Note

class RecyclerNoteAdapter : BaseRecyclerAdapter<Note>() {

    override fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_note_card, parent, false)
        return NoteViewHolder(view)
    }

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position = position)
        (viewHolder as NoteViewHolder).bind(note = note)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvHeadline = itemView.findViewById(R.id.tvHeadline) as TextView
        private var tvContent = itemView.findViewById(R.id.tvContent) as TextView

        fun bind(note: Note) {
            tvHeadline.text = note.title
            tvContent.text = note.content
        }
    }
}