package com.moussafir.notesapp.domain.use_case

import com.moussafir.notesapp.data.repository.NoteRepository
import com.moussafir.notesapp.domain.model.Note
import com.moussafir.notesapp.util.InvalidNoteException
import javax.inject.Inject
import kotlin.jvm.Throws

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isEmpty()) {
            throw InvalidNoteException("Note title can not be empty")
        }
        repository.insertNote(note)
    }

}