package com.codespacepro.moviecompose.di.ktor.client

import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.util.Constant.Companion.AUTHORIZATION
import com.codespacepro.moviecompose.util.Constant.Companion.BEARER_TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object MovieApiClient {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {

            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
        defaultRequest {
            header(
                AUTHORIZATION,
                BEARER_TOKEN
            )
        }
    }

    suspend fun getPopular(page: Int): Movies {
        val url = "https://api.themoviedb.org/3/movie/popular?language=en-US&page=${page}"
        return client.get(url).body()
    }

    suspend fun getSearched(query: String): Movies {
        val url =
            "https://api.themoviedb.org/3/search/movie?query=${query}&include_adult=false&language=en-US&page=1"
        return client.get(url).body()
    }

}