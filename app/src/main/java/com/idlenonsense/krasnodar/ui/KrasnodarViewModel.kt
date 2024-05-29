package com.idlenonsense.krasnodar.ui

import androidx.lifecycle.ViewModel
import com.idlenonsense.krasnodar.data.Category
import com.idlenonsense.krasnodar.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KrasnodarViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(KrasnodarAppState())
    val uiState: StateFlow<KrasnodarAppState> = _uiState.asStateFlow()

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
}