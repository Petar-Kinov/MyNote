package com.example.mynote.repository

import androidx.lifecycle.LiveData
import com.example.mynote.database.NotesDao
import com.example.mynote.modelClass.Note

class NoteRepository(private val notesDao: NotesDao) {

    val allnotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note){
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }

 }