package com.example.tasques.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tasques.data.entity.TascaTag

@Dao
interface TascaTagDao {
    @Insert
    suspend fun insertTascaTag(tascaTag: TascaTag)

    @Delete
    suspend fun deleteTascaTag(tascaTag: TascaTag)

    @Query("DELETE FROM tasca_tag WHERE tascaId = :tascaId")
    suspend fun deleteTagsForTasca(tascaId: Int)

    @Query("SELECT * FROM tasca_tag WHERE tascaId = :tascaId")
    suspend fun getTagsForTasca(tascaId: Int): List<TascaTag>

    @Query("SELECT * FROM tasca_tag WHERE tagId = :tagId")
    suspend fun getTasquesForTag(tagId: Int): List<TascaTag>
}
