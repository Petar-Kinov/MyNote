package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.constants.Constants
import com.example.mynote.modelClass.Note
import com.example.mynote.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEditText: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addUpdateButton: Button
    lateinit var viewModel: NoteViewModel
    var noteID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEditText = findViewById(R.id.editNoteTitle)
        noteDescriptionEdit = findViewById(R.id.editNoteDescription)
        addUpdateButton = findViewById(R.id.addupdateButton)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra(Constants.MyConstants.NOTE_TYPE)

        if (noteType.equals(Constants.MyConstants.EDIT)){
            val noteTitle = intent.getStringExtra(Constants.MyConstants.NOTE_TITLE)
            val noteDescription = intent.getStringExtra(Constants.MyConstants.NOTE_DESCRIPTION)
            noteID = intent.getIntExtra(Constants.MyConstants.NOTE_ID, -1)
            addUpdateButton.setText(Constants.MyConstants.UPDATE_NOTE)
            noteTitleEditText.setText(noteTitle)
            noteDescriptionEdit.setText(noteDescription)
        } else{
            addUpdateButton.setText(Constants.MyConstants.SAVE_NOTE)
        }

        addUpdateButton.setOnClickListener{
            val noteTitle = noteTitleEditText.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()

            if (noteType.equals(Constants.MyConstants.EDIT)){
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDescription,currentDate)
                    updateNote.id = noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated..", Toast.LENGTH_LONG).show()
                    }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDescription,currentDate))
                    Toast.makeText(this,"Note Added..", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}