package com.example.tasques.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import com.example.tasques.data.TasquesRepository
import com.example.tasques.data.entity.TascaWithTags

class FilterTascasByTagViewModel(private val repository: TasquesRepository) : ViewModel() {
    private val selectedTagId = MutableLiveData<Int>()

    val filteredTasques: LiveData<List<TascaWithTags>> = selectedTagId.switchMap { tagId ->
        repository.getTasquesByTag(tagId)
    }

    fun setSelectedTag(tagId: Int) {
        selectedTagId.value = tagId
    }
}

class FilterTascasByTagViewModelFactory(private val repository: TasquesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterTascasByTagViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterTascasByTagViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
