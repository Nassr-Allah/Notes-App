package com.moussafir.notesapp.ui.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moussafir.notesapp.domain.model.Note
import com.moussafir.notesapp.ui.view.add_note_view.AddNoteScreen
import com.moussafir.notesapp.ui.view.note_list_view.NoteListScreen
import com.moussafir.notesapp.ui.view.note_preview_view.NotePreviewScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.NoteListScreen.route) {
        composable(route = Routes.NoteListScreen.route) {
            NoteListScreen(navController = navController) {
                navController.navigate(it.route)
            }
        }
        composable(
            route = Routes.AddNoteScreen.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddNoteScreen(navController = navController)
        }
        composable(
            route = Routes.NotePreviewScreen.route + "?noteId={noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            NotePreviewScreen(navController = navController)
        }
    }
}