package com.example.lab8moviles.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.data.LocationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class LocationDetailState(
    val isLoading: Boolean = true,
    val data: LocationEntity? = null,
    val hasError: Boolean = false
)

class LocationDetailViewModel(private val db: AppDatabase, private val id: Int) : ViewModel() {
    private val _state = MutableStateFlow(LocationDetailState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = LocationDetailState(isLoading = true)
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                val location = db.locationDao().getLocationById(id)
                _state.value = LocationDetailState(isLoading = false, data = location, hasError = false)
            } else {
                _state.value = LocationDetailState(isLoading = false, hasError = true)
            }
        }
    }
}