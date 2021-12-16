package com.aslansari.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.aslansari.notes.note.Note
import com.aslansari.notes.ui.recycler.RecyclerMarginDecorator
import com.aslansari.notes.ui.recycler.RecyclerNoteAdapter

class MainFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerNotes = rootView.findViewById(R.id.recyclerNotes) as RecyclerView
        val fabMain = rootView.findViewById(R.id.fabMain) as FloatingActionButton
        val ivAddNote = rootView.findViewById(R.id.ivAddNote) as ImageView

        ivAddNote.visibility = View.GONE

        val recyclerAdapter = RecyclerNoteAdapter()
        recyclerNotes.layoutManager = LinearLayoutManager(activity)
        val verticalMargin = resources.getDimensionPixelSize(R.dimen.note_item_margin_vertical)
        val horizontalMargin = resources.getDimensionPixelSize(R.dimen.note_item_margin_horizontal)
        recyclerNotes.addItemDecoration(RecyclerMarginDecorator(verticalMargin, horizontalMargin))
        recyclerNotes.adapter = recyclerAdapter

        // TODO: 12/5/2021 remove fake data
        recyclerAdapter.addAll(fakeData().toMutableList())

        fabMain.setOnClickListener {
            val data = Bundle()
            data.putString("title", context?.getString(R.string.add_note))
            findNavController().navigate(R.id.action_nav_home_to_nav_note, data)
        }
        return rootView
    }
}

// TODO: 12/5/2021 added for viewing reasons,
//  remove when note feature implemented
fun fakeData(): List<Note> {
    return listOf(
        Note(
            title = "Title1",
            content = "Content",
            imageUrl = "",
            createdAt = "",
            editedAt = ""
        ),
        Note(
            title = "Title2",
            content = "Content",
            imageUrl = "",
            createdAt = "",
            editedAt = ""
        ),
        Note(
            title = "Title3",
            content = "Content",
            imageUrl = "",
            createdAt = "",
            editedAt = ""
        )
    )
}