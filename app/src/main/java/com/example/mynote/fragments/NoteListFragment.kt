package com.example.mynote.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.adapters.NoteAdapter
import com.example.mynote.constants.Constants
import com.example.mynote.databinding.FragmentNoteListBinding
import com.example.mynote.modelClass.Note
import com.example.mynote.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListFragment : Fragment(),
    NoteAdapter.NoteClickInterface,
    NoteAdapter.NoteClickDeleteInterface {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get()= _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        addFAB = binding.floatingActionButton
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(NoteViewModel::class.java)
        val noteRecyclerAdapter = NoteAdapter(requireContext(),this,this)
        recyclerView.adapter = noteRecyclerAdapter

        viewModel.allNotes.observe(viewLifecycleOwner) { list ->
            noteRecyclerAdapter.submitList(list)
        }

        addFAB.setOnClickListener{
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(noteType = Constants.MyConstants.ADD,null,null,-1 )
            it.findNavController().navigate(action)
        }

    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(requireContext(),"${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(Constants.MyConstants.EDIT,note.noteTitle,note.noteDescription,note.id)
        this.findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}