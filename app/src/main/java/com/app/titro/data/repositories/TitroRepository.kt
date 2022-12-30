package com.app.titro.data.repositories

import com.app.titro.data.TitroDao
import com.app.titro.data.models.TitroNote
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TitroRepository @Inject constructor(private val titroDao: TitroDao) {

    val getAllNotes: Flow<List<TitroNote>> = titroDao.getAllNotes()
    val sortByLowPriority: Flow<List<TitroNote>> = titroDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<TitroNote>> = titroDao.sortByHighPriority()

    fun getSelectedNote(noteId: Int): Flow<TitroNote> {
        return titroDao.getSelectedNote(noteId = noteId)
    }

    suspend fun addNote(titroNote: TitroNote) {
        titroDao.addNote(titroNote = titroNote)
    }

    suspend fun updateNote(titroNote: TitroNote) {
        titroDao.updateNote(titroNote = titroNote)
    }

    suspend fun deleteNote(titroNote: TitroNote) {
        titroDao.deleteNote(titroNote = titroNote)
    }

    suspend fun deleteAllNotes() {
        titroDao.deleteAllNotes()
    }

    fun searchDatabase(searchQuery: String): Flow<List<TitroNote>> {
        return titroDao.searchDatabase(searchQuery = searchQuery)
    }

}