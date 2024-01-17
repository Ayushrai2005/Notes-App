package com.example.room

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.example.room.db.Note
import com.example.room.db.NoteDataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() , NotesAdapter.ClickListener {

    private lateinit var repo : Repo
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesViewModelFactory: NotesViewModelFactory
    private lateinit var notesDatabase: NoteDataBase

    private lateinit var rv : RecyclerView
    private lateinit var notesAdapter : NotesAdapter

    private lateinit var edtnoteName : EditText
    private lateinit var edtnoteContent :EditText
    private lateinit var btnSave : Button

    private lateinit var dialog : Dialog
    private lateinit var fab : FloatingActionButton






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    init()



        notesViewModel.getAllNotes().observe(this){
            //observer block
            notesAdapter = NotesAdapter (it)
            rv.adapter = notesAdapter
            rv.layoutManager = LinearLayoutManager(this)



        }

        fab.setOnClickListener {
            openDialog()
        }

    }

    private fun openDialog(comingFromFloatingActionButton : Boolean){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog)

        edtnoteName = dialog.findViewById(R.id.edt_note_name)
        edtnoteContent= dialog.findViewById(R.id.edt_note_content)
        btnSave  =dialog.findViewById(R.id.btn_save)


        btnSave.setOnClickListener {
            val note = Note(
                noteName = edtnoteName.text.toString(),
                noteContent = edtnoteContent.text.toString()
            )

            if(comingFromFloatingActionButton)

            notesViewModel.insert(note)
            dialog.dismiss()
        }
        dialog.show()


    }

    private fun init(){
        notesDatabase = NoteDataBase(this)
        repo = Repo (notesDatabase.getNoteDao())

        notesViewModelFactory = NotesViewModelFactory(repo)
        notesViewModel = ViewModelProvider(this, notesViewModelFactory).get(NotesViewModel::class.java)
        rv = findViewById(R.id.rv)
        fab = findViewById(R.id.fab)
        
    }

    override fun updateNote() {
        //write the logic of updating
        openDialog()

    }
}