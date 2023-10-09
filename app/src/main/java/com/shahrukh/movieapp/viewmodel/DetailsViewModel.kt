package com.shahrukh.movieapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahrukh.movieapp.model.Cast
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.repository.FilmRepository
import com.shahrukh.movieapp.utils.FilmType
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






}