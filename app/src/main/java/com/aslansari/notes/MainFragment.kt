package com.aslansari.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_main, container, false)
        val textView = rootView.findViewById(R.id.tvMain) as TextView
        textView.setOnClickListener {
            val data = Bundle()
            data.putString("title", "Note")
            findNavController().navigate(R.id.action_nav_home_to_nav_note, data)
        }
        return rootView
    }
}