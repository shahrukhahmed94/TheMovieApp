package com.shahrukh.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahrukh.movieapp.data.remote.response.Review
import com.shahrukh.movieapp.repository.ReviewsRepository
import com.shahrukh.movieapp.utils.FilmType

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repo: ReviewsRepository) : ViewModel() {

    private val _filmReviews = mutableStateOf<Flow<PagingData<Review>>>(emptyFlow())
    val filmReviews: State<Flow<PagingData<Review>>> = _filmReviews

    fun getFilmReview(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            _filmReviews.value = repo.getFilmReviews(filmId = filmId, filmType).cachedIn(viewModelScope)
        }
    }
}