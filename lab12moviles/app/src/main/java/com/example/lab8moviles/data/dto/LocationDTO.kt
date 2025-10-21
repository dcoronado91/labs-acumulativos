package com.example.lab8moviles.data.dto

import kotlinx.serialization.Serializable
import com.example.lab8moviles.data.LocationEntity

@Serializable
data class LocationResponse(
    val info: Info,
    val results: List<LocationDTO>
)

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
) {
    fun toEntity() = LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,
        url = url,
        created = created
    )
}