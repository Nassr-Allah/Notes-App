package com.moussafir.notesapp.domain.use_case

import com.moussafir.notesapp.data.repository.NoteRepository
import com.moussafir.notesapp.domain.model.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }

}