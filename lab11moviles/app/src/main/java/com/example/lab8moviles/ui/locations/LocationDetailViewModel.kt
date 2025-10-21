package com.example.lab8moviles.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.example.lab8moviles.data.Location
import com.example.lab8moviles.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class LocationDetailState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)

class LocationDetailViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _state = MutableStateFlow(LocationDetailState())
    val state = _state.asStateFlow()

    private val locationId: Int = savedStateHandle.get<Int>("locationId") ?: 0 // Safe get with default

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = LocationDetailState(isLoading = true)
            delay(2000)
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                val db = LocationDb()
                _state.value = LocationDetailState(isLoading = false, data = db.getLocationById(locationId), hasError = false)
            } else {
                _state.value = LocationDetailState(isLoading = false, hasError = true)
            }
        }
    }
}