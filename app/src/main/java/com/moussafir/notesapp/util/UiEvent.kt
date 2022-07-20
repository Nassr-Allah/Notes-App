package com.moussafir.notesapp.util

import com.moussafir.notesapp.domain.model.Note

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String, val note: Note? = null): UiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}
