package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.adapters.NoteAdapter
import com.example.mynote.adapters.NoteClickDeleteInterface
import com.example.mynote.adapters.NoteClickInterface
import com.example.mynote.constants.Constants
import com.example.mynote.modelClass.Note
import com.example.mynote.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),NoteClickInterface, NoteClickDeleteInterface {

    lateinit var recyclerView: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addFAB = findViewById(R.id.floatingActionButton)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteRecyclerAdapter = NoteAdapter(this,this,this)
        recyclerView.adapter = noteRecyclerAdapter
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteRecyclerAdapter.updateList(it)
            }
        })

        addFAB.setOnClickListener{
            val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra(Constants.MyConstants.NOTE_TYPE, Constants.MyConstants.EDIT)
        intent.putExtra(Constants.MyConstants.NOTE_TITLE, note.noteTitle)
        intent.putExtra(Constants.MyConstants.NOTE_DESCRIPTION, note.noteDescription)
        intent.putExtra(Constants.MyConstants.NOTE_ID, note.id)
        startActivity(intent)
        this.finish()
    }
}