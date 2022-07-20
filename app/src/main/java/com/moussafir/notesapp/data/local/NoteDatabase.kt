package com.moussafir.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moussafir.notesapp.domain.model.Note


@Database(entities = [Note::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

}