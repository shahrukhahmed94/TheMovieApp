package com.shahrukh.movieapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.shahrukh.movieapp.data.remote.ApiService
import com.shahrukh.movieapp.model.Search
import com.shahrukh.movieapp.paging.SearchFilmSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: ApiService
) {
    fun multiSearch(searchParams: String, includeAdult: Boolean): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SearchFilmSource(api = api, searchParams = searchParams, includeAdult)
            }
        ).flow
    }
}