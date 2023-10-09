package com.codespacepro.moviecompose.di.network

import com.codespacepro.moviecompose.di.api.MoviesApi
import com.codespacepro.moviecompose.util.Constant.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val interceptor = Interceptor { interceptor ->
        val originalRequest = interceptor.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlYjE2ODA5YWI0NzU5ZWMyMzBmYmM4MWQzNjZiZDM1MSIsInN1YiI6IjYyZDUzY2ZlNzJjMTNlMDYyZmVkYmU2NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.fsCUNzYIaSPkJ5Phuf1k1-iJyNb4_jIx88p_A4h4XM8"
            )
            .build()
        interceptor.proceed(newRequest)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val moviesApi: MoviesApi by lazy {
        retrofit.create(MoviesApi::class.java)
    }
}