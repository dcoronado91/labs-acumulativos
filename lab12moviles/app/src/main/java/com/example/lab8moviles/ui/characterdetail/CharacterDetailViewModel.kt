package com.example.lab8moviles.ui.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.data.CharacterEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val data: CharacterEntity? = null,
    val hasError: Boolean = false
)

class CharacterDetailViewModel(private val db: AppDatabase, private val id: Int) : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = CharacterDetailState(isLoading = true)
            delay(2000) // 2 seconds loading
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                val character = db.characterDao().getCharacterById(id)
                _state.value = CharacterDetailState(isLoading = false, data = character, hasError = false)
            } else {
                _state.value = CharacterDetailState(isLoading = false, hasError = true)
            }
        }
    }
}