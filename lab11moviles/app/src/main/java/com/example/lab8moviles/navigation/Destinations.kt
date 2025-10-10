package com.example.lab8moviles.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations {
    @Serializable
    object Login

    @Serializable
    object Main

    @Serializable
    object Characters

    @Serializable
    data class CharacterDetail(val characterId: Int)

    @Serializable
    object Locations

    @Serializable
    data class LocationDetail(val locationId: Int)

    @Serializable
    object Profile
}