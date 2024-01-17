package com.example.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.room.db.Note
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(private val repo: Repo) : ViewModel() {


    fun getAllNotes() = repo.getAllNotes()

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            repo.insert(note)
        }

    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            repo.delete(note)
        }

    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            repo.update(note)
        }

    }


}