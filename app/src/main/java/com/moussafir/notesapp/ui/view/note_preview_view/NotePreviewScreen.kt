package com.moussafir.notesapp.ui.view.note_preview_view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.moussafir.notesapp.R
import com.moussafir.notesapp.ui.theme.Orange
import kotlin.math.max

@Composable
fun NotePreviewScreen(
    viewModel: NotePreviewViewModel = hiltViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState(0)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        backgroundColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                elevation = FloatingActionButtonDefaults.elevation(5.dp),
                backgroundColor = Color(viewModel.colorValue)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 30.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = viewModel.title,
                    style = MaterialTheme.typography.h1,
                    color = Color(viewModel.colorValue),
                    fontSize = 35.sp,
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = viewModel.description,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.verticalScroll(state = scrollState, enabled = true)
                )
            }
        }
    }
}