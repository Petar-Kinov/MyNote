package com.example.mynote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynote.database.NoteDatabase
import com.example.mynote.modelClass.Note
import com.example.mynote.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allnotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        repository.delete(note)
    }
     fun updateNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
         repository.update(note)
     }

    fun addNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(note)
    }
}