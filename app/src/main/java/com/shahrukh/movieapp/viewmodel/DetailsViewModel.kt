package com.shahrukh.movieapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahrukh.movieapp.data.remote.response.WatchProvider

import com.shahrukh.movieapp.model.Cast
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.repository.FilmRepository
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repository: FilmRepository) : ViewModel() {
    private var _similarFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val similarMovies: State<Flow<PagingData<Film>>> = _similarFilms

    private var _filmCast = mutableStateOf<List<Cast>>(emptyList())
    val filmCast: State<List<Cast>> = _filmCast

    private var _watchProviders = mutableStateOf<WatchProvider?>(null)
    val watchProviders: MutableState<WatchProvider?> = _watchProviders

    fun getSimilarFilms(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            repository.getSimilarFilms(filmId, filmType).also {
                _similarFilms.value = it
            }.cachedIn(viewModelScope)
        }
    }


    fun getFilmCast(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            repository.getFilmCast(filmId = filmId, filmType).also {
                if (it is Resource.Success) {
                    _filmCast.value = it.data!!.castResult
                }
            }
        }
    }

    fun getWatchProviders(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            repository.getWatchProviders(filmType = filmType, filmId = filmId).also {
                if (it is Resource.Success) {
                    _watchProviders.value = it.data!!.results
                }
            }
        }
    }
}

