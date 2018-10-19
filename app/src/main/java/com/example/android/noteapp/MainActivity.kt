package com.example.android.noteapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android.noteapp.data.Note
import com.example.android.noteapp.items.NoteItemSmall
import com.example.android.noteapp.note.AddNoteActivity
import com.example.android.noteapp.note.NoteViewModel
import com.example.android.noteapp.settings.SettingsActivity
import com.example.android.noteapp.signin.SignInActivity
import com.example.android.noteapp.utilities.AuthUtil
import com.example.android.noteapp.utilities.InjectorUtils
import com.example.android.noteapp.utilities.ThemeUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var singleColumn: Boolean = false
    private var data: List<Note>? = null
    lateinit var groupAdapter: GroupAdapter<ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(my_toolbar)

        //Set a title for the action bar
        supportActionBar?.title = "Notes"

        //Fab onclick
        add_note_fab.setOnClickListener {
            //Starting AddNoteActivity
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }


        //Checking if the User is logged in
        if (!AuthUtil.getAuthState()) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }


        //Initialize the recycler adapter and set the spanCount
        groupAdapter = GroupAdapter<ViewHolder>().apply {
            spanCount = 2
        }

        //Ably the span count and the adapter to the recyclerview
        note_recycler_view.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            singleColumn = false
            adapter = groupAdapter
        }

        //Initialising the UI
        initializeUI()
    }

    override fun onStart() {
        super.onStart()

        ThemeUtil.setTheme(this, delegate)
    }

    /**
     * Initialize the UI and update it with Livedata
     */
    private fun initializeUI(){
        val factory = InjectorUtils.provideNotesViewModelFactory()

        val viewModel = ViewModelProviders.of(this, factory).get(NoteViewModel::class.java)

        viewModel.getNotes().observe(this, Observer {notes ->
            data = null

            data = notes

            updateAdapter()
        })

    }

    /**
     * Update the adapter when the data changes
     */
    private fun updateAdapter(){
        groupAdapter.clear()
        data!!.forEach {
            groupAdapter.add(NoteItemSmall(it, this))
        }
    }

    /**
     * Inflate the toolbar menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Onclick Events for the toolbar items
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            //Logout the user and send him to the login screen
            R.id.logout -> {
                AuthUtil.logout()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }

            //Change the columns of the recycler view
            R.id.change_recyclerview_columns -> {

                if(!singleColumn){
                    note_recycler_view.apply {
                        layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                        singleColumn = true
                    }
                }else{
                    note_recycler_view.apply {
                        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        singleColumn = false
                    }
                }

            }

            R.id.settings ->{
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

