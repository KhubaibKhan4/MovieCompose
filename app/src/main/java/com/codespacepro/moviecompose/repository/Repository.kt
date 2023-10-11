package com.codespacepro.moviecompose.repository

import com.codespacepro.moviecompose.di.network.RetrofitInstance
import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.model.person.Person
import com.codespacepro.moviecompose.model.tv.Tv
import retrofit2.Response

class Repository {

    suspend fun getPopular(lang: String, page: Int): Response<Movies> {
        return RetrofitInstance.moviesApi.getPopular(lang = lang, page = page)
    }

    suspend fun getTopRated(lang: String, page: Int): Response<Movies> {
        return RetrofitInstance.moviesApi.getTopRated(lang = lang, page = page)
    }

    suspend fun getUpComing(lang: String, page: Int): Response<Movies> {
        return RetrofitInstance.moviesApi.getUpComing(lang = lang, page = page)
    }

    suspend fun getTrendingToday(lang: String): Response<Movies> {
        return RetrofitInstance.moviesApi.getTrendingToday(lang = lang)
    }

    suspend fun getTrendingWeekly(lang: String): Response<Movies> {
        return RetrofitInstance.moviesApi.getTrendingWeek(lang = lang)
    }

    suspend fun getTrendingPersonToday(lang: String): Response<Person> {
        return RetrofitInstance.moviesApi.getTrendingPersonToday(lang = lang)
    }

    suspend fun getTrendingPersonWeekly(lang: String): Response<Person> {
        return RetrofitInstance.moviesApi.getTrendingPersonWeek(lang = lang)
    }

    suspend fun getTrendingTVWeekly(lang: String): Response<Tv> {
        return RetrofitInstance.moviesApi.getTrendingTVWeek(lang = lang)
    }

    suspend fun getTrendingTVToday(lang: String): Response<Tv> {
        return RetrofitInstance.moviesApi.getTrendingTVWeek(lang = lang)
    }

    suspend fun getTrendingMovie(lang: String): Response<Movies> {
        return RetrofitInstance.moviesApi.getTrendingMovie(lang = lang)
    }

    suspend fun getTvTopRatedSeries(lang: String, page: Int): Response<Tv> {
        return RetrofitInstance.moviesApi.getTvTopRatedSeries(lang = lang, page)
    }

    suspend fun getSimilar(lang: String, page: Int, movieId: Int): Response<Movies> {
        return RetrofitInstance.moviesApi.getSimilar(lang = lang, movie_id = movieId, page = page)
    }

    suspend fun getRecommendation(lang: String, page: Int, movieId: Int): Response<Movies> {
        return RetrofitInstance.moviesApi.getRecommendation(
            lang = lang,
            movie_id = movieId,
            page = page
        )
    }

    suspend fun getSearchMovie(
        query: String,
        include_adult: Boolean,
        lang: String,
        page: Int,
    ): Response<Movies> {
        return RetrofitInstance.moviesApi.getSearchedMovie(
            query,
            include_adult,
            lang = lang,
            page = page
        )
    }

}
