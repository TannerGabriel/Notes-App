package com.example.android.noteapp.utilities

import com.example.android.noteapp.note.NoteRepository
import com.example.android.noteapp.note.NoteViewModelProvider

object InjectorUtils {
    fun provideNotesViewModelFactory(): NoteViewModelProvider {
        val noteRepository = NoteRepository.getInstance()
        return NoteViewModelProvider(noteRepository)
    }
}