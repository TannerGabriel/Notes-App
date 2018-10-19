package com.example.android.noteapp.note


import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.android.noteapp.MainActivity
import com.example.android.noteapp.R
import com.example.android.noteapp.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_notes.*

class AddNoteActivity : AppCompatActivity() {


    //Get the viewmodel factory
    var factory: NoteViewModelProvider? = null

    //Getting the viewmodel
    var viewModel : NoteViewModel ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        //Set the toolbar a the support actionbar
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Note"

        factory = InjectorUtils.provideNotesViewModelFactory()
        viewModel  = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)

        image_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    /**
     * Inflate the toolbar menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_notes_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handle the onclick Events for the toolbar items
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        when (item?.itemId) {
            //Getting the data from the text fields and saving them in the database
           R.id.add_note ->{
               val title = title_edittext.text.toString()
               val details = note_edittext.text.toString()



               if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(details)){
                   Toast.makeText(this, "Adding Note!", Toast.LENGTH_SHORT).show()


                   var imageUri = viewModel!!.getDownloadUri()

                   if(selectedPhoto != null){
                       viewModel!!.addNote(title, details, imageUri!!, this)
                   }else{
                       viewModel!!.addNote(title, details, "", this)
                   }



                   val intent = Intent(this, MainActivity::class.java)
                   startActivity(intent)
                   finish()
               }else{
                   Toast.makeText(this, "Both fields have to be filled out to continue!", Toast.LENGTH_SHORT).show()
               }


           }
        }
        return super.onOptionsItemSelected(item)
    }

    var selectedPhoto: Uri? = null

    /**
     * Getting the selected picture and saving it in the database
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        selectedPhoto = null

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhoto = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhoto)

            Glide.with(this).load(bitmap).into(header_image)


        }

        if(selectedPhoto != null){
            viewModel!!.uploadImage(selectedPhoto!!, this)
        }
    }



}
