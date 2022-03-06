package com.example.notesroomdb.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notesroomdb.database.entity.Note

@Dao
interface NoteDao {

    @Insert()
    fun insertNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getNoteList(): List<Note>

    @Query("DELETE FROM notes")
    fun clearAll()


    @Query("DELETE FROM notes WHERE noteId=:productID")
    fun removeNote(productID: Int)






}