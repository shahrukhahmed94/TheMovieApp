package com.shahrukh.movieapp.repository


import com.shahrukh.movieapp.data.remote.ApiService
import com.shahrukh.movieapp.data.remote.response.GenreResponse
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class GenreRepository @Inject constructor(private val api: ApiService) {
    suspend fun getMoviesGenre(filmType: FilmType): Resource<GenreResponse> {
        val response = try {
            if (filmType == FilmType.MOVIE) api.getMovieGenres() else api.getTvShowGenres()
        } catch (e: Exception){
            return Resource.Error("Unknown error occurred!")
        }
        return Resource.Success(response)
    }
}