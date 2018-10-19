package com.example.android.noteapp.note

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.android.noteapp.data.Note


class NoteViewModel(private val noteRepository: NoteRepository): ViewModel(){

    fun getNotes() = noteRepository.getNotes()

    fun addNote(title: String, details: String, imageUrl: String, context: Context) = noteRepository.addNote(title, details, imageUrl, context)

    fun updateNote(note: Note, context: Context) = noteRepository.updateNote(note, context)

    fun deleteNote(id: String, context: Context) = noteRepository.deleteNote(id, context)

    fun uploadImage(pictureUri: Uri, context: Context) = noteRepository.uploadImage(pictureUri, context)

    fun getDownloadUri() = noteRepository.getDownloadUri()
}