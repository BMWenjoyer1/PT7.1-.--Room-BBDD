package com.example.tasques.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tasques.data.entity.Tag

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: Tag): Long

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tags ORDER BY nom ASC")
    fun getAllTags(): LiveData<List<Tag>>

    @Query("SELECT * FROM tags ORDER BY nom ASC")
    suspend fun getAllTagsSync(): List<Tag>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagById(id: Int): Tag?

    @Query("SELECT COUNT(*) FROM tags")
    suspend fun getTagCount(): Int
}
