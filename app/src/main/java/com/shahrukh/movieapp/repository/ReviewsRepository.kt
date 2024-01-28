package com.shahrukh.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.shahrukh.movieapp.data.remote.ApiService
import com.shahrukh.movieapp.data.remote.response.Review
import com.shahrukh.movieapp.paging.ReviewsSource
import com.shahrukh.movieapp.utils.FilmType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewsRepository @Inject constructor(private val api: ApiService) {

    fun getFilmReviews(filmId: Int, filmType: FilmType): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                ReviewsSource(api = api, filmId = filmId, filmType = filmType)
            }
        ).flow
    }
}