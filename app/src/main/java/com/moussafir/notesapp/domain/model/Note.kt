package com.moussafir.notesapp.domain.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val isPrivate: Boolean = false,
    val color: Long = 0xFFF1F2F6
) : Parcelable