package com.example.mynote.modelClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note(
    @ColumnInfo(name = "title") val noteTitle: String,
    @ColumnInfo(name = "description") val noteDescription: String,
    @ColumnInfo(name = "timeStamp") val timeStamp: String
) {
    @PrimaryKey(autoGenerate = true) var id = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (noteTitle != other.noteTitle) return false
        if (noteDescription != other.noteDescription) return false
        if (timeStamp != other.timeStamp) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = noteTitle.hashCode()
        result = 31 * result + noteDescription.hashCode()
        result = 31 * result + timeStamp.hashCode()
        result = 31 * result + id
        return result
    }


}