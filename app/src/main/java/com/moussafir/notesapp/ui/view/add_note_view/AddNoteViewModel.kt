package com.moussafir.notesapp.ui.view.add_note_view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moussafir.notesapp.domain.model.Note
import com.moussafir.notesapp.domain.use_case.InsertNoteUseCase
import com.moussafir.notesapp.domain.use_case.NoteUseCases
import com.moussafir.notesapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                noteUseCases.getNoteByIdUseCase(noteId).let { note ->
                    title = note?.title ?: ""
                    description = note?.description ?: ""
                    colorValue = note?.color ?: 0xFFFFFFFF
                    this@AddNoteViewModel.note = note
                }
            }
        }
    }

    fun onEvent(event: AddNoteEvent) {
        when(event) {
            is AddNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    val newNote = Note(
                        title = title,
                        description = description,
                        color = colorValue,
                        id = note?.id
                    )
                    noteUseCases.insertNoteUseCase(newNote)
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is AddNoteEvent.OnTitleChange -> {
                title = event.title
            }
            is AddNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddNoteEvent.OnColorChange -> {
                colorValue = event.colorValue
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}