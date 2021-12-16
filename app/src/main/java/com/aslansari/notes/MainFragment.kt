package com.aslansari.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslansari.notes.ui.recycler.RecyclerMarginDecorator
import com.aslansari.notes.ui.recycler.RecyclerNoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect

class MainFragment: Fragment() {

    val noteViewModel: NoteViewModel by activityViewModels()

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

        lifecycleScope.launchWhenStarted {
            noteViewModel.noteFlow.collect {
                ivAddNote.visibility = if (it.isEmpty()) {
                    View.VISIBLE
                } else {
                    recyclerAdapter.update(it)
                    View.GONE
                }
            }
        }

        recyclerAdapter.OnItemClickListener = {
            val data = Bundle()
            data.putString("title", context?.getString(R.string.edit_note))
            data.putString("id", it.id)
            findNavController().navigate(R.id.action_nav_home_to_nav_note, data)
        }

        fabMain.setOnClickListener {
            val data = Bundle()
            data.putString("title", context?.getString(R.string.add_note))
            findNavController().navigate(R.id.action_nav_home_to_nav_note, data)
        }
        return rootView
    }
}