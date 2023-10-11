package com.codespacepro.moviecompose.di.api

import com.codespacepro.moviecompose.model.Movies
import com.codespacepro.moviecompose.model.person.Person
import com.codespacepro.moviecompose.model.tv.Tv
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {


    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<Movies>


    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<Movies>


    @GET("3/movie/upcoming")
    suspend fun getUpComing(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("3/trending/movie/day")
    suspend fun getTrendingMovie(
        @Query("language") lang: String,
    ): Response<Movies>



    @GET("3/tv/airing_today")
    suspend fun getTvTodaySeries(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<Movies>


    @GET("3/tv/popular")
    suspend fun getTvPopularSeries(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<Movies>


    @GET("3/tv/top_rated")
    suspend fun getTvTopRatedSeries(
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<Tv>



    @GET("3/trending/all/day")
    suspend fun getTrendingToday(
        @Query("language") lang: String
    ): Response<Movies>


    @GET("3/trending/all/week")
    suspend fun getTrendingWeek(
        @Query("language") lang: String
    ): Response<Movies>



    @GET("3/trending/movie/day")
    suspend fun getTrendingMovieToday(
        @Query("language") lang: String
    ): Response<Movies>


    @GET("3/trending/movie/week")
    suspend fun getTrendingMovieWeek(
        @Query("language") lang: String
    ): Response<Movies>



    @GET("3/trending/person/day")
    suspend fun getTrendingPersonToday(
        @Query("language") lang: String
    ): Response<Person>


    @GET("3/trending/person/week")
    suspend fun getTrendingPersonWeek(
        @Query("language") lang: String
    ): Response<Person>



    @GET("3/trending/tv/day")
    suspend fun getTrendingTVToday(
        @Query("language") lang: String
    ): Response<Tv>


    @GET("3/trending/tv/week")
    suspend fun getTrendingTVWeek(
        @Query("language") lang: String
    ): Response<Tv>



    @GET("3/certification/movie/list")
    suspend fun getCertificationMovies(): Response<Movies>


    @GET("3/certification/tv/list")
    suspend fun getCertificationTv(): Response<Movies>



    //sort_by -- --- popularity.desc
    @GET("3/discover/movie")
    suspend fun getDiscoverMovie(
        @Query("include_video") include_video: Boolean,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("sort_by") sort_by: String
    ): Response<Movies>


    //sort_by -- --- popularity.desc
    @GET("3/discover/tv")
    suspend fun getDiscoverTv(
        @Query("include_video") include_video: Boolean,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("sort_by") sort_by: String
    ): Response<Movies>



    //sort_by -- --- created_at.asc
    @GET("3/guest_session/{guest_session_id}/rated/movies")
    suspend fun getRelatedMovie(
        @Path("guest_session_id") guest_session_id: String,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("sort_by") sort_by: String
    ): Response<Movies>


    //sort_by -- --- created_at.asc
    @GET("3/guest_session/{guest_session_id}/rated/tv")
    suspend fun getRelatedTv(
        @Path("guest_session_id") guest_session_id: String,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("sort_by") sort_by: String
    ): Response<Movies>


    @GET("3/movie/{movie_id}/recommendations")
    suspend fun getRecommendation(
        @Path("movie_id") movie_id: Int,
        @Query("language") lang: String,
        @Query("page") page: Int,
    ): Response<Movies>

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movie_id: Int,
        @Query("language") lang: String,
        @Query("page") page: Int,
    ): Response<Movies>

    @GET("3/search/movie")
    suspend fun getSearchedMovie(
        @Query("query") query: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("language") lang: String,
        @Query("page") page: Int,
    ): Response<Movies>
}