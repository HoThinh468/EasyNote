package com.example.easynote.android.notelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easynote.domain.note.Note
import com.example.easynote.domain.note.NoteDataSource
import com.example.easynote.domain.note.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNoteUC = SearchNoteUseCase()

    private val notes = savedStateHandle.getStateFlow(NOTES_STATE_KEY, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SEARCH_TEXT_STATE_KEY, "")
    private val isSearchActive = savedStateHandle.getStateFlow(IS_SEARCH_ACTIVE_STATE_KEY, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNoteUC.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), NoteListState())

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[NOTES_STATE_KEY] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChanged(text: String) {
        savedStateHandle[SEARCH_TEXT_STATE_KEY] = text
    }

    fun onToggleSearch() {
        savedStateHandle[IS_SEARCH_ACTIVE_STATE_KEY] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle[SEARCH_TEXT_STATE_KEY] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }

    companion object {
        private const val NOTES_STATE_KEY = "notes"
        private const val SEARCH_TEXT_STATE_KEY = "searchText"
        private const val IS_SEARCH_ACTIVE_STATE_KEY = "isSearchActive"
    }
}