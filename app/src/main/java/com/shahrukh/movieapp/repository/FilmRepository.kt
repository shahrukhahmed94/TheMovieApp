package com.shahrukh.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shahrukh.movieapp.data.remote.ApiService
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.paging.PopularFilmSource
import com.shahrukh.movieapp.paging.RecommendedFilmSource
import com.shahrukh.movieapp.utils.FilmType
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















}