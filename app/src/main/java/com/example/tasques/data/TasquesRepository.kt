package com.example.tasques.data

import androidx.lifecycle.LiveData
import com.example.tasques.data.dao.TascaDao
import com.example.tasques.data.dao.TagDao
import com.example.tasques.data.dao.TascaTagDao
import com.example.tasques.data.entity.Tasca
import com.example.tasques.data.entity.Tag
import com.example.tasques.data.entity.TascaTag
import com.example.tasques.data.entity.TascaWithTags

class TasquesRepository(
    private val tascaDao: TascaDao,
    private val tagDao: TagDao,
    private val tascaTagDao: TascaTagDao
) {
    // Tasques
    fun getAllTasques(): LiveData<List<Tasca>> = tascaDao.getAllTasques()
    fun getAllTasquesWithTags(): LiveData<List<TascaWithTags>> =
        tascaDao.getAllTasquesWithTags()

    suspend fun insertTasca(tasca: Tasca): Long = tascaDao.insertTasca(tasca)
    suspend fun updateTasca(tasca: Tasca) = tascaDao.updateTasca(tasca)
    suspend fun deleteTasca(tasca: Tasca) = tascaDao.deleteTasca(tasca)

    // Tags
    fun getAllTags(): LiveData<List<Tag>> = tagDao.getAllTags()
    suspend fun getAllTagsSync(): List<Tag> = tagDao.getAllTagsSync()
    suspend fun insertTag(tag: Tag): Long = tagDao.insertTag(tag)
    suspend fun updateTag(tag: Tag) = tagDao.updateTag(tag)
    suspend fun deleteTag(tag: Tag) = tagDao.deleteTag(tag)

    // Tasca-Tag relationships
    fun getTasquesByTag(tagId: Int): LiveData<List<TascaWithTags>> =
        tascaDao.getTasquesByTag(tagId)

    suspend fun assignTagToTasca(tascaId: Int, tagId: Int) {
        tascaTagDao.insertTascaTag(TascaTag(tascaId, tagId))
    }

    suspend fun removeTagFromTasca(tascaId: Int, tagId: Int) {
        tascaTagDao.deleteTascaTag(TascaTag(tascaId, tagId))
    }

    suspend fun updateTascaTags(tascaId: Int, tagIds: List<Int>) {
        // Remove all existing tags
        tascaTagDao.deleteTagsForTasca(tascaId)
        
        // Add new tags
        tagIds.forEach { tagId ->
            tascaTagDao.insertTascaTag(TascaTag(tascaId, tagId))
        }
    }

    suspend fun getTascaWithTagsById(id: Int): TascaWithTags? =
        tascaDao.getTascaWithTagsById(id)
}
