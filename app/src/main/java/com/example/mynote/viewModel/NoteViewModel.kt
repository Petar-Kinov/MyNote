package com.example.mynote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynote.database.NoteDatabase
import com.example.mynote.modelClass.Note
import com.example.mynote.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val _updateNoteStare = MutableStateFlow<UpdateNoteState>(UpdateNoteState.Empty)
    val updateNoteState : StateFlow<UpdateNoteState> = _updateNoteStare

    val allNotes = MutableLiveData<List<Note>>()
    val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        viewModelScope.launch{
            repository.allNotes.collect{
                allNotes.postValue(it)
            }
        }
    }

    sealed class UpdateNoteState {
        object Success: UpdateNoteState()
        data class Error(val message: String): UpdateNoteState()
        object Loading: UpdateNoteState()
        object Empty: UpdateNoteState()
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