package com.example.notesroomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesroomdb.database.dao.NoteDao
import com.example.notesroomdb.database.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDataBase? = null

        @Synchronized
        fun getInstance(context: Context):NoteDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, NoteDataBase::class.java, "note.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }


    }
}