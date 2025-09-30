package com.example.lab8moviles.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.Location
import com.example.lab8moviles.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class LocationsState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)

class LocationsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = LocationsState(isLoading = true)
            delay(4000)
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                val db = LocationDb()
                _state.value = LocationsState(isLoading = false, data = db.getAllLocations(), hasError = false)
            } else {
                _state.value = LocationsState(isLoading = false, hasError = true)
            }
        }
    }
}