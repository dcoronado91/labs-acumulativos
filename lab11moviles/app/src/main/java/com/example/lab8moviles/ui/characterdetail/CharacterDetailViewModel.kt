package com.example.lab8moviles.ui.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.example.lab8moviles.data.Character
import com.example.lab8moviles.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)

class CharacterDetailViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    private val characterId: Int = savedStateHandle.get<Int>("characterId") ?: 0 // Safe get with default

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = CharacterDetailState(isLoading = true)
            delay(2000)
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                val db = CharacterDb()
                _state.value = CharacterDetailState(isLoading = false, data = db.getCharacterById(characterId), hasError = false)
            } else {
                _state.value = CharacterDetailState(isLoading = false, hasError = true)
            }
        }
    }
}