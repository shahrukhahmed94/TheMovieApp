package com.shahrukh.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shahrukh.movieapp.data.remote.ApiService
import com.shahrukh.movieapp.data.remote.response.CastResponse
import com.shahrukh.movieapp.data.remote.response.WatchProviderResponse
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.paging.PopularFilmSource
import com.shahrukh.movieapp.paging.RecommendedFilmSource
import com.shahrukh.movieapp.paging.SimilarFilmSource
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val api: ApiService
) {


    fun getPopularFilms(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                PopularFilmSource(api = api, filmType)
            }
        ).flow
    }

    fun getRecommendedFilms(movieId: Int, filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                RecommendedFilmSource(api = api, filmId = movieId, filmType)
            }
        ).flow
    }


    fun getSimilarFilms(movieId: Int, filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SimilarFilmSource(api = api, filmId = movieId, filmType)
            }
        ).flow
    }

    suspend fun getFilmCast(filmId: Int, filmType: FilmType): Resource<CastResponse> {
        val response = try {
            if (filmType == FilmType.MOVIE) api.getMovieCast(filmId = filmId)
            else api.getTvShowCast(filmId = filmId)
        } catch (e: Exception) {
            return Resource.Error("Error when loading movie cast")
        }
        return Resource.Success(response)
    }

    suspend fun getWatchProviders(
        filmType: FilmType, filmId: Int
    ): Resource<WatchProviderResponse> {
        val response = try {
            if (filmType == FilmType.MOVIE) api.getWatchProviders(
                filmPath = "movie", filmId = filmId
            )
            else api.getWatchProviders(filmPath = "tv", filmId = filmId)
        } catch (e: Exception) {
            return Resource.Error("Error when loading providers")
        }
        Timber.d("WATCH", response)
        return Resource.Success(response)
    }













}