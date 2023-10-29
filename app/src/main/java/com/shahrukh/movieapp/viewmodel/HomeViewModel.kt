package com.shahrukh.movieapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.model.Genre
import com.shahrukh.movieapp.repository.FilmRepository
import com.shahrukh.movieapp.repository.GenreRepository
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {


    private var _filmGenres = mutableStateListOf(Genre(null, "All"))
    val filmGenres: SnapshotStateList<Genre> = _filmGenres

    private var _popularFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val popularFilmsState: State<Flow<PagingData<Film>>> = _popularFilms

    var selectedGenre: MutableState<Genre> = mutableStateOf(Genre(null, "All"))
    var selectedFilmType: MutableState<FilmType> = mutableStateOf(FilmType.MOVIE)


    init {
        refreshAll()
    }

    fun refreshAll(
        genreId: Int? = selectedGenre.value.id,
        filmType: FilmType = selectedFilmType.value
    ) {

        getPopularFilms(genreId, filmType)


    }


    private fun getPopularFilms(genreId: Int?, filmType: FilmType) {
        viewModelScope.launch {
            _popularFilms.value = if (genreId != null) {
                filmRepository.getPopularFilms(filmType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getPopularFilms(filmType).cachedIn(viewModelScope)
            }
        }
    }

    fun getFilmGenre(filmType: FilmType = selectedFilmType.value) {
        viewModelScope.launch {
            val defaultGenre = Genre(null, "All")
            when (val results = genreRepository.getMoviesGenre(filmType)) {
                is Resource.Success -> {
                    _filmGenres.clear()
                    _filmGenres.add(defaultGenre)
                    selectedGenre.value = defaultGenre
                    results.data?.genres?.forEach {
                        _filmGenres.add(it)
                    }
                }
                is Resource.Error -> {
                    Timber.e("Error loading Genres")
                }
                else -> { }
            }
        }
    }






}