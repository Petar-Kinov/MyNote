package com.example.mynote.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mynote.constants.Constants
import com.example.mynote.databinding.FragmentNoteDetailBinding
import com.example.mynote.modelClass.Note
import com.example.mynote.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailFragment : Fragment() {
    private var _binding : FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var noteTitleEditText: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addUpdateButton: Button
    lateinit var viewModel: NoteViewModel
    lateinit var noteType: String
    lateinit var noteTitle: String
    lateinit var noteDescription: String
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteType = it.getString(Constants.MyConstants.NOTE_TYPE).toString()
            noteTitle = it.getString(Constants.MyConstants.NOTE_TITLE).toString()
            noteDescription = it.getString(Constants.MyConstants.NOTE_DESCRIPTION).toString()
            noteID = it.getInt(Constants.MyConstants.NOTE_ID)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            addUpdateNote()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteTitleEditText = binding.editNoteTitle
        noteDescriptionEdit = binding.editNoteDescription
        addUpdateButton = binding.addupdateButton
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(NoteViewModel::class.java)

        if (noteType.equals(Constants.MyConstants.EDIT)){
            addUpdateButton.setText(Constants.MyConstants.UPDATE_NOTE)
            noteTitleEditText.setText(noteTitle)
            noteDescriptionEdit.setText(noteDescription)
        } else{
            addUpdateButton.setText(Constants.MyConstants.SAVE_NOTE)
        }

        addUpdateButton.setOnClickListener{
            addUpdateNote()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteListFragment()
                binding.root.findNavController().navigate(action)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun addUpdateNote(){
        val noteTitle = noteTitleEditText.text.toString()
        val noteDescription = noteDescriptionEdit.text.toString()

        if (noteType.equals(Constants.MyConstants.EDIT)){
            if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                val currentDate: String = sdf.format(Date())
                val updateNote = Note(noteTitle,noteDescription,currentDate)
                updateNote.id = noteID
                viewModel.updateNote(updateNote)
                Toast.makeText(requireContext(),"Note Updated..", Toast.LENGTH_LONG).show()
            }
        } else {
            if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                val currentDate: String = sdf.format(Date())
                viewModel.addNote(Note(noteTitle,noteDescription,currentDate))
                Toast.makeText(requireContext(),"Note Added..", Toast.LENGTH_LONG).show()
            }
        }
        val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteListFragment()
        binding.root.findNavController().navigate(action)
    }



}