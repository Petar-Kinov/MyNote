package com.example.mynote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynote.modelClass.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note:Note)

    @Delete
    suspend fun delete(note: Note)

   @Query("SELECT * FROM notesTable order by id ASC")
   fun getAllNotes(): LiveData<List<Note>>

}