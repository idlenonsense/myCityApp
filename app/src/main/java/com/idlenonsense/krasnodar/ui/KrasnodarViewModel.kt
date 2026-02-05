package com.idlenonsense.krasnodar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.idlenonsense.krasnodar.KrasnodarApplication
import com.idlenonsense.krasnodar.data.Category
import com.idlenonsense.krasnodar.data.Place
import com.idlenonsense.krasnodar.data.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Collections.emptyList

class KrasnodarViewModel(private val repository: PlaceRepository): ViewModel() {
    private val _uiState = MutableStateFlow(KrasnodarAppState())
    val uiState: StateFlow<KrasnodarAppState> = _uiState.asStateFlow()

    val currentPlaces: StateFlow<List<Place>> = _uiState
        .map { it.currentCategory }
        .flatMapLatest { category ->
            repository.getPlacesByCategory(category)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun changeCurrentCategory(newCategory: Category) {
        _uiState.update {
            it.copy(currentCategory = newCategory)
        }
    }

    fun changeCurrentPlace(newPlace: Place) {
        _uiState.update {
            it.copy(currentPlace = newPlace)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }


    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as KrasnodarApplication
                return KrasnodarViewModel(application.repository) as T
            }
        }
    }
}