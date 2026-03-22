package com.example.tasques.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tasques.data.TasquesRepository
import com.example.tasques.data.entity.Tag
import kotlinx.coroutines.launch

class TagViewModel(private val repository: TasquesRepository) : ViewModel() {
    val allTags: LiveData<List<Tag>> = repository.getAllTags()

    fun addTag(tagName: String) {
        viewModelScope.launch {
            val tag = Tag(nom = tagName)
            repository.insertTag(tag)
        }
    }

    fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            repository.deleteTag(tag)
        }
    }
}

class TagViewModelFactory(private val repository: TasquesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TagViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
