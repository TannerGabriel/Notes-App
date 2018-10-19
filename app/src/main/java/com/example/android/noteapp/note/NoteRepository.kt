package com.example.android.noteapp.note

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.android.noteapp.MainActivity
import com.example.android.noteapp.data.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class NoteRepository {

    companion object {

        @Volatile
        private var instance: NoteRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: NoteRepository().also { instance = it }
                }


    }

    init {
        getAllNotes()
    }

    var uid = FirebaseAuth.getInstance().addAuthStateListener {
        getAllNotes()
    }

    private val notes = MutableLiveData<List<Note>>()

    /**
     * Get all the Notes from the database and same them in the notes variable
     */
    private fun getAllNotes() {
        var uid = FirebaseAuth.getInstance().uid.toString()
        val mFirestore = FirebaseFirestore.getInstance()

        mFirestore.collection("users").document(uid).collection("notes")
                .addSnapshotListener { documentSnapshot, error ->
                    val notes = ArrayList<Note>()
                    if (documentSnapshot != null) {
                        documentSnapshot.documents.forEach {
                            val title: String = it.data?.get("title").toString()
                            val detail: String = it.data?.get("detail").toString()
                            val imageUrl: String = it.data?.get("imageUrl").toString()
                            val id: String = it.data?.get("id").toString()
                            var note = Note(title, detail, imageUrl, id)
                            notes.add(note)
                        }
                    }
                    this.notes.value = notes
                }
    }

    /**
     * Returning all the notes objects
     */
    fun getNotes() = notes


    /**
     * Adding notes to database
     * @param title The title of the note
     * @param details Details of the note
     * @param imageUrl The url of the image used in the note
     * @param The context of the Activity that calls this method
     */
    fun addNote(title: String, details: String, imageUrl: String, context: Context) {
        var uid = FirebaseAuth.getInstance().uid.toString()
        val mFirestore = FirebaseFirestore.getInstance()

        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var item_id = ""
        for (i in 0..31) {
            item_id += chars[Math.floor(Math.random() * chars.length).toInt()]
        }

        val note = Note(title, details, imageUrl, item_id)

        mFirestore.collection("users").document(uid).collection("notes").document(item_id)
                .set(note)
                .addOnSuccessListener {
                    Toast.makeText(context, "Succesfully added Note!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }.addOnFailureListener {
                    Toast.makeText(context, "Error while adding Note: $it", Toast.LENGTH_SHORT).show()
                }
    }

    /**
     * Updating notes in database
     *
     * @param note The updated Note object that will get saved
     * @param context The context of the Activity calling the function
     */
    fun updateNote(note: Note, context: Context){
        var uid = FirebaseAuth.getInstance().uid.toString()
        val mFirestore = FirebaseFirestore.getInstance()

        val item_id = note.id

        mFirestore.collection("users").document(uid).collection("notes").document(item_id)
                .update("title", note.title,
                        "detail", note.detail,
                        "imageUrl", note.imageUrl)
                .addOnSuccessListener {
                    //Open MainActivity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error while updating: $it", Toast.LENGTH_SHORT).show()
                }
    }

    /**
     *  Deleting a note from the database
     *
     * @param id The id of the note that is going to be deleted
     * @param context The context of the Activity calling the function
     */
    fun deleteNote(id: String, context: Context){
        var uid = FirebaseAuth.getInstance().uid.toString()
        val mFirestore = FirebaseFirestore.getInstance()

        mFirestore.collection("users").document(uid).collection("notes").document(id)
                .delete()
                .addOnSuccessListener {
                    //Open MainActivity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error while deleting: $it", Toast.LENGTH_SHORT).show()
                }
    }

    private var downloadUri: String? = null


    /**
     * Returning the download uri of the image
     */
    fun getDownloadUri() = downloadUri

    /**
     * Uploading a image to Firebase Storage
     *
     * @param uri The picture uri
     * @param context Context of the Activity calling the function
     */
    fun uploadImage(uri : Uri, context: Context){
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var item_id = ""
        for (i in 0..31) {
            item_id += chars[Math.floor(Math.random() * chars.length).toInt()]
        }


        var uid = FirebaseAuth.getInstance().uid.toString()

        val mStorage = FirebaseStorage.getInstance().getReference("/users/$uid/$item_id")



        mStorage.putFile(uri)
                .addOnSuccessListener {

                    mStorage.downloadUrl.addOnSuccessListener {
                        Toast.makeText(context, "Successfully uploaded image!", Toast.LENGTH_LONG).show()
                        downloadUri = it.toString()
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Error while uploading: $it", Toast.LENGTH_SHORT).show()
                    Log.d("Test", it.toString())
                }

    }
}