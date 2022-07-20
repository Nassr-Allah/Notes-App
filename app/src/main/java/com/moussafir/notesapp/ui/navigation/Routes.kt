package com.moussafir.notesapp.ui.navigation

sealed class Routes(val route: String) {
    object NoteListScreen : Routes("note_list_screen")
    object AddNoteScreen : Routes("add_note_screen")
    object NotePreviewScreen : Routes("note_preview_screen")
}