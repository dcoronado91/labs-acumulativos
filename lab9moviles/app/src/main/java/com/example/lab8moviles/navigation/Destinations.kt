package com.example.lab8moviles.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Characters

@Serializable
data class CharacterDetail(val characterId: Int)