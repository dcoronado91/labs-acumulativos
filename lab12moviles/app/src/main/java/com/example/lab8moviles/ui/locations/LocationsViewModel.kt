package com.example.lab8moviles.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab8moviles.data.AppDatabase
import com.example.lab8moviles.data.LocationEntity
import com.example.lab8moviles.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class LocationsState(
    val isLoading: Boolean = true,
    val data: List<LocationEntity> = emptyList(),
    val hasError: Boolean = false
)

class LocationsViewModel(private val db: AppDatabase) : ViewModel() {
    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = LocationsState(isLoading = true)
            val random = Random.nextInt(1, 11)
            if (random % 2 == 0) {
                var locations = db.locationDao().getAllLocations()
                if (locations.isEmpty()) {
                    try {
                        val response = ApiClient.getLocations()
                        locations = response.results.map { it.toEntity() }
                        db.locationDao().insertAll(locations)
                    } catch (e: Exception) {
                        _state.value = LocationsState(isLoading = false, hasError = true)
                        return@launch
                    }
                }
                _state.value = LocationsState(isLoading = false, data = locations, hasError = false)
            } else {
                _state.value = LocationsState(isLoading = false, hasError = true)
            }
        }
    }
}