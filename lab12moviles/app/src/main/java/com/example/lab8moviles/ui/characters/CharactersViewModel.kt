package com.example.lab8moviles.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.data.CharacterEntity
import com.example.lab8moviles.network.ApiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class CharactersState(
    val isLoading: Boolean = true,
    val data: List<CharacterEntity> = emptyList(),
    val hasError: Boolean = false
)

class CharactersViewModel(private val db: AppDatabase) : ViewModel() {
    private val _state = MutableStateFlow(CharactersState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = CharactersState(isLoading = true)
            delay(4000) // 4 seconds loading
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                var characters = db.characterDao().getAllCharacters()
                if (characters.isEmpty()) {
                    val response = ApiClient.getCharacters()
                    characters = response.results.map { it.toEntity() }
                    db.characterDao().insertAll(characters)
                }
                _state.value = CharactersState(isLoading = false, data = characters, hasError = false)
            } else {
                _state.value = CharactersState(isLoading = false, hasError = true)
            }
        }
    }
}