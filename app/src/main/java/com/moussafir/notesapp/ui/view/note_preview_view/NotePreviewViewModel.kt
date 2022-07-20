package com.moussafir.notesapp.ui.view.note_preview_view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moussafir.notesapp.domain.model.Note
import com.moussafir.notesapp.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotePreviewViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var note by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var colorValue by mutableStateOf(0xFFFFFFFF)
        private set

    init {
        val noteId = savedStateHandle.get<Int>("noteId")!!
        if (noteId != -1) {
            viewModelScope.launch {
                noteUseCases.getNoteByIdUseCase(noteId).let { selectedNote ->
                    title = selectedNote?.title ?: ""
                    description = selectedNote?.description ?: ""
                    colorValue = selectedNote?.color ?: 0xFFFFFFFF
                }
            }
        }
    }
}