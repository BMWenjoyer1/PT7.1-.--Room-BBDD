package com.example.tasques.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.tasques.data.entity.Tasca
import com.example.tasques.data.entity.TascaWithTags

@Dao
interface TascaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasca(tasca: Tasca): Long

    @Update
    suspend fun updateTasca(tasca: Tasca)

    @Delete
    suspend fun deleteTasca(tasca: Tasca)

    @Query("SELECT * FROM tasques ORDER BY dataCreacio DESC")
    fun getAllTasques(): LiveData<List<Tasca>>

    @Transaction
    @Query("SELECT * FROM tasques ORDER BY dataCreacio DESC")
    fun getAllTasquesWithTags(): LiveData<List<TascaWithTags>>

    @Transaction
    @Query("""
        SELECT DISTINCT t.* FROM tasques t
        INNER JOIN tasca_tag tt ON t.id = tt.tascaId
        WHERE tt.tagId = :tagId
        ORDER BY t.dataCreacio DESC
    """)
    fun getTasquesByTag(tagId: Int): LiveData<List<TascaWithTags>>

    @Query("SELECT * FROM tasques WHERE id = :id")
    suspend fun getTascaById(id: Int): Tasca?

    @Transaction
    @Query("SELECT * FROM tasques WHERE id = :id")
    suspend fun getTascaWithTagsById(id: Int): TascaWithTags?
}
