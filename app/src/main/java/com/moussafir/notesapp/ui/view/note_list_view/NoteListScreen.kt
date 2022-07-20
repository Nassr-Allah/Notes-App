package com.moussafir.notesapp.ui.view.note_list_view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moussafir.notesapp.R
import com.moussafir.notesapp.domain.model.Note
import com.moussafir.notesapp.ui.components.CustomSnackBar
import com.moussafir.notesapp.ui.components.NoteItem
import com.moussafir.notesapp.ui.navigation.Routes
import com.moussafir.notesapp.ui.theme.DarkGray
import com.moussafir.notesapp.ui.theme.Gray
import com.moussafir.notesapp.ui.theme.NotesAppTheme
import com.moussafir.notesapp.ui.theme.Orange
import com.moussafir.notesapp.util.UiEvent
import kotlinx.coroutines.flow.collect
import java.lang.Long.parseLong


@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    navController: NavController,
    onNavigate: (UiEvent.Navigate) -> Unit
) {

    val notes = viewModel.allNotes.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.RestoreNote)
                    }
                }
                is UiEvent.Navigate -> {
                    Log.d("NoteListScreen", event.toString())
                    onNavigate(event)
                }
                else -> {
                    navController.popBackStack()
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Gray,
        scaffoldState = scaffoldState,
        snackbarHost = {
            CustomSnackBar(it) {
                it.currentSnackbarData?.performAction()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(NoteListEvent.AddNote)
                },
                elevation = FloatingActionButtonDefaults.elevation(5.dp),
                backgroundColor = Orange,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column {
            if (notes.isEmpty()) {
                EmptyNotesScreen()
            } else {
                NoteList(notes = notes, viewModel = viewModel)
            }
        }
    }

}

@Composable
fun NoteList(notes: List<Note>, viewModel: NoteListViewModel) {
    Header()
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(notes) { note ->
            Log.d("NoteListScreen", "color: ${note.color}")
            NoteItem(
                title = note.title,
                description = note.description,
                colorValue = note.color,
                onEdit = {
                    viewModel.onEvent(NoteListEvent.EditNote(note))
                },
                onDelete = {
                    viewModel.onEvent(NoteListEvent.DeleteNote(note))
                },
                onView = {
                    viewModel.onEvent(NoteListEvent.ViewNote(note))
                }
            )
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_sticky_note),
                contentDescription = null,
                tint = DarkGray,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "NOTES",
                style = MaterialTheme.typography.h1,
                color = DarkGray,
                fontSize = 14.sp
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            tint = Color.Transparent,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun EmptyNotesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Header()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "You don't have any notes yet",
                style = MaterialTheme.typography.h1,
                color = DarkGray,
                fontSize = 22.sp,
                modifier = Modifier.fillMaxWidth(0.5f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                Image(
                    painter = painterResource(R.drawable.illustration),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Add Notes Now!",
                style = MaterialTheme.typography.h1,
                color = Orange,
                fontSize = 22.sp
            )
        }
    }
}
