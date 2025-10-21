package com.example.lab8moviles.network

import com.example.lab8moviles.data.dto.CharacterResponse
import com.example.lab8moviles.data.dto.LocationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getCharacters(): CharacterResponse {
        return client.get("https://rickandmortyapi.com/api/character").body()
    }

    suspend fun getLocations(): LocationResponse {
        return client.get("https://rickandmortyapi.com/api/location").body()
    }
}