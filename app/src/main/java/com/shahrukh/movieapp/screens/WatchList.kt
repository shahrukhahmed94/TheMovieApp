package com.shahrukh.movieapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shahrukh.movieapp.viewmodel.SearchViewModel
import com.shahrukh.movieapp.viewmodel.WatchListViewModel

@Destination
@Composable
fun WatchList(
    navigator: DestinationsNavigator,
    watchListViewModel: WatchListViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {

}