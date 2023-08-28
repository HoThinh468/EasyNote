package com.example.easynote.android.notelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadNotes()
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { }, backgroundColor = Color.Black) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Add note", tint = Color.White
            )
        }
    }, topBar = {
        HideableTextField(
            text = state.searchText,
            isSearchActive = state.isSearchActive,
            onTextChange = viewModel::onSearchTextChanged,
            onSearchClick = viewModel::onToggleSearch,
            onCloseClick = viewModel::onToggleSearch
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

        }
    }
}