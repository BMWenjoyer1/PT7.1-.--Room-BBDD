package com.example.tasques.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasques.data.TasquesRepository
import com.example.tasques.data.entity.Tasca
import com.example.tasques.data.entity.TascaWithTags
import kotlinx.coroutines.launch

class TascaListViewModel(private val repository: TasquesRepository) : ViewModel() {
    val allTasques: LiveData<List<TascaWithTags>> = repository.getAllTasquesWithTags()

    fun deleteTasca(tasca: Tasca) {
        viewModelScope.launch {
            repository.deleteTasca(tasca)
        }
    }

    fun updateTascaEstat(tasca: Tasca, nouEstat: String) {
        viewModelScope.launch {
            val tascaActualitzada = tasca.copy(
                estat = nouEstat,
                dataCanvi = System.currentTimeMillis()
            )
            repository.updateTasca(tascaActualitzada)
        }
    }
}

class TascaListViewModelFactory(private val repository: TasquesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TascaListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TascaListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
