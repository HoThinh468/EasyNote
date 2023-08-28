package com.example.easynote.data.note

import com.example.easynote.database.NoteDatabase
import com.example.easynote.domain.note.Note
import com.example.easynote.domain.note.NoteDataSource
import com.example.easynote.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(db: NoteDatabase) : NoteDataSource {
    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            note.id,
            note.title,
            note.content,
            note.colorHex,
            DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes().executeAsList().map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}