package com.example.lab8moviles.data

fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    species = species,
    status = status,
    gender = gender,
    image = image
)

fun Location.toEntity() = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)