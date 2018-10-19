package com.example.android.noteapp.note


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.android.noteapp.R
import com.example.android.noteapp.data.Note
import com.example.android.noteapp.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        setSupportActionBar(note_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        initializeUI()
    }

    private var note: Note? = null

    /**
     * Initializes the ui
     */
    private fun initializeUI() {
        val intent = intent
        note = intent.getParcelableExtra<Note>("note")

        note_title_edittext.setText(note!!.title)
        note_detail_edittext.setText(note!!.detail)

        Glide.with(this).load(note?.imageUrl).into(note_picture_imageview)
    }


    /**
     * Inflate the toolbar menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.change_notes_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handle the onclick Events for the toolbar items
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //Get the viewmodel factory
        val factory = InjectorUtils.provideNotesViewModelFactory()

        //Getting the viewmodel
        val viewModel = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)

        when (item?.itemId) {
            R.id.delete_note ->{
                if(note != null){
                    viewModel.deleteNote(note!!.id, this)
                }
            }

            R.id.save_note ->{
                if(note != null){
                    val title = note_title_edittext.text.toString()
                    val detail = note_detail_edittext.text.toString()

                    if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(detail)){
                        val updatedNote = Note(title, detail, note!!.imageUrl, note!!.id)

                        viewModel.updateNote(updatedNote, this)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
