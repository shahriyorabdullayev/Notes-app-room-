package com.example.notesroomdb.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int? = null,
    var date: String,
    var note: String

)
