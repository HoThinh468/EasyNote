package com.example.easynote.domain.note

import com.example.easynote.domain.time.DateTimeUtil

class SearchNoteUseCase {
    fun execute(notes: List<Note>, query: String): List<Note> {
        if (query.isEmpty()) {
            return notes
        }
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) || it.content.lowercase()
                .contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}