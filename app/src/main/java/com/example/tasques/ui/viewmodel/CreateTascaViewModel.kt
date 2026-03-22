package com.example.tasques.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tasques.data.TasquesRepository
import com.example.tasques.data.entity.Tasca
import com.example.tasques.data.entity.Tag
import kotlinx.coroutines.launch

class CreateTascaViewModel(private val repository: TasquesRepository) : ViewModel() {
    val allTags: LiveData<List<Tag>> = repository.getAllTags()

    fun saveTasca(titol: String, descripcio: String, selectedTagIds: List<Int>) {
        viewModelScope.launch {
            val tasca = Tasca(
                titol = titol,
                descripcio = descripcio,
                estat = "pendent",
                dataCreacio = System.currentTimeMillis()
            )

            val id = repository.insertTasca(tasca).toInt()

            // Assign tags to the newly created task
            selectedTagIds.forEach { tagId ->
                repository.assignTagToTasca(id, tagId)
            }
        }
    }
}

class CreateTascaViewModelFactory(private val repository: TasquesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTascaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateTascaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
