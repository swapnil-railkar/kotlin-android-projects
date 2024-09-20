package com.example.reciepeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Error


class MainViewModel: ViewModel() {

    data class RecipeState (
        val loading : Boolean = true,
        val categories: List<Category> = emptyList(),
        val error: String ?= null
    )

    private val _categorySate = mutableStateOf(RecipeState())
    val categoryState : State<RecipeState> = _categorySate

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                _categorySate.value = _categorySate.value.copy(
                    loading = false,
                    categories = recipeService.getCategories().categories,
                    error = null
                )
            } catch (e: Exception) {
                _categorySate.value = _categorySate.value.copy(
                    loading = false,
                    error = "Error occurred while fetching data ${e.message}"
                )
            }
        }
    }

}