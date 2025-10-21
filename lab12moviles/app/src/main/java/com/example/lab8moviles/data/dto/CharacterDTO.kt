package com.example.lab8moviles.data.dto

import kotlinx.serialization.Serializable
import com.example.lab8moviles.data.CharacterEntity

@Serializable
data class CharacterResponse(
    val info: Info,
    val results: List<CharacterDTO>
)

@Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDTO,
    val location: CharacterLocationDTO,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    fun toEntity() = CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originName = origin.name,
        originUrl = origin.url,
        locationName = location.name,
        locationUrl = location.url,
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}

@Serializable
data class OriginDTO(
    val name: String,
    val url: String
)

@Serializable
data class CharacterLocationDTO(
    val name: String,
    val url: String
)