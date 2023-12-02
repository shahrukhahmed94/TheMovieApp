package com.shahrukh.movieapp.data.remote

import com.shahrukh.movieapp.data.remote.response.FilmIDResponse
import com.shahrukh.movieapp.data.remote.response.FilmResponse
import com.shahrukh.movieapp.data.remote.response.GenreResponse
import com.shahrukh.movieapp.data.remote.response.MultiSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): FilmResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieByID(
        @Path("movie_id") filmId: Int,
        @Query("api_key") apiKey: String =  "64e371e5a440e766829afdecba6ae279"
    ): FilmIDResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): GenreResponse

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 0,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("api_key") apiKey: String =  "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): MultiSearchResponse

    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en-US"
    ): GenreResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): FilmResponse


    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en-US"
    ): FilmResponse

}