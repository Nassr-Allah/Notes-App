package com.moussafir.notesapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moussafir.notesapp.R
import com.moussafir.notesapp.ui.theme.DarkGray
import com.moussafir.notesapp.ui.theme.Orange

@Composable
fun NoteItem(
    title: String,
    description: String,
    colorValue: Long,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onView: () -> Unit
) {
    var sizeState by remember {
        mutableStateOf(1f)
    }
    var alphaState by remember {
        mutableStateOf(0f)
    }
    val width by animateFloatAsState(
        targetValue = sizeState,
        tween(
            durationMillis = 300,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    val alpha by animateFloatAsState(
        targetValue = alphaState,
        tween(
            durationMillis = 500,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )
    val scrollState = rememberScrollState(0)
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(11.dp))
            .background(Color(colorValue))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(width)
                    .heightIn(min = 50.dp, max = 150.dp)
                    .clickable {
                        sizeState = if (sizeState == 1f) 0.8f else 1f
                        alphaState = if (alphaState == 1f) 0f else 1f
                    },
                backgroundColor = Color.White,
                elevation = 3.dp,
                shape = RoundedCornerShape(11.dp)
            ) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 15.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(colorValue))
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = title,
                            color = Color.Black,
                            style = MaterialTheme.typography.h1,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = description,
                            color = Color.Black,
                            modifier = Modifier.alpha(0.5f).verticalScroll(state = scrollState, enabled = true).fillMaxWidth(0.7f),
                            style = MaterialTheme.typography.body1,
                            fontSize = 11.sp
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_eye),
                            contentDescription = null,
                            tint = Color(colorValue),
                            modifier = Modifier
                                .size(25.dp)
                                .alpha(alpha)
                                .clickable {
                                    onView()
                                }
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(alpha),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onEdit()
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onDelete()
                        }
                )
            }
        }
    }
}

@Composable
fun CustomSnackBar(snackbarHostState: SnackbarHostState, onActionClick: () -> Unit) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Card(
                shape = RoundedCornerShape(5.dp),
                elevation = 3.dp,
                backgroundColor = DarkGray,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = snackbarData.message,
                        style = MaterialTheme.typography.h1,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = snackbarData.actionLabel ?: "",
                        style = MaterialTheme.typography.h1,
                        color = Orange,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            onActionClick()
                        }
                    )
                }
            }
        }
    )

}