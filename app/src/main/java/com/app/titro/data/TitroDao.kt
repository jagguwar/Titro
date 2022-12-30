package com.app.titro.data

import androidx.room.*
import com.app.titro.data.models.TitroNote
import kotlinx.coroutines.flow.Flow

@Dao
interface TitroDao {

    @Query("SELECT * FROM titro_table ORDER BY id ASC")
    fun getAllNotes(): Flow<List<TitroNote>>

    @Query("SELECT * FROM titro_table WHERE id=:noteId")
    fun getSelectedNote(noteId: Int): Flow<TitroNote>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(titroNote: TitroNote)

    @Update
    suspend fun updateNote(titroNote: TitroNote)

    @Delete
    suspend fun deleteNote(titroNote: TitroNote)

    @Query("DELETE FROM titro_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM titro_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<TitroNote>>

    @Query(
        """
        SELECT * FROM titro_table ORDER BY
    CASE
        WHEN priority LIKE 'L%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'H%' THEN 3
    END
    """
    )
    fun sortByLowPriority(): Flow<List<TitroNote>>

    @Query(
        """
        SELECT * FROM titro_table ORDER BY
    CASE
        WHEN priority LIKE 'H%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'L%' THEN 3
    END
    """
    )
    fun sortByHighPriority(): Flow<List<TitroNote>>

}