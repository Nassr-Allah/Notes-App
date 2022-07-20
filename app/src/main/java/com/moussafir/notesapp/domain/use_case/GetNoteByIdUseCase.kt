package com.moussafir.notesapp.domain.use_case

import com.moussafir.notesapp.data.repository.NoteRepository
import com.moussafir.notesapp.domain.model.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }

}