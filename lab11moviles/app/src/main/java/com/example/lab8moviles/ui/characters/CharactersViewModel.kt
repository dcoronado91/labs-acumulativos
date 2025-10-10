package com.example.lab8moviles.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.Character
import com.example.lab8moviles.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class CharactersState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false
)

class CharactersViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharactersState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = CharactersState(isLoading = true)
            delay(4000)
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                val db = CharacterDb()
                _state.value = CharactersState(isLoading = false, data = db.getAllCharacters(), hasError = false)
            } else {
                _state.value = CharactersState(isLoading = false, hasError = true)
            }
        }
    }
}