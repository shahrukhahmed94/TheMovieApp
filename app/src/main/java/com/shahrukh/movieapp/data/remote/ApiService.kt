package com.shahrukh.movieapp.data.remote

import com.shahrukh.movieapp.data.remote.response.CastResponse
import com.shahrukh.movieapp.data.remote.response.FilmIDResponse
import com.shahrukh.movieapp.data.remote.response.FilmResponse
import com.shahrukh.movieapp.data.remote.response.GenreResponse
import com.shahrukh.movieapp.data.remote.response.MultiSearchResponse
import com.shahrukh.movieapp.data.remote.response.ReviewsResponse
import com.shahrukh.movieapp.data.remote.response.WatchProviderResponse
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



    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey:  String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey:  String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendedTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("{film_path}/{film_id}/watch/providers?")
    suspend fun getWatchProviders(
        @Path("film_path") filmPath: String,
        @Path("film_id") filmId: Int,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
    ): WatchProviderResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") filmId: Int,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279"
    ): CastResponse


    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") filmId: Int,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279"
    ): CastResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("{film_path}/{film_id}/reviews?")
    suspend fun getMovieReviews(
        @Path("film_id") filmId: Int,
        @Path("film_path") filmPath: String,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "64e371e5a440e766829afdecba6ae279",
        @Query("language") language: String = "en-US"
    ): ReviewsResponse


}