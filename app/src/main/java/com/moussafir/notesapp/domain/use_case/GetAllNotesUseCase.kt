package com.moussafir.notesapp.domain.use_case

import com.moussafir.notesapp.data.repository.NoteRepository
import com.moussafir.notesapp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }

}