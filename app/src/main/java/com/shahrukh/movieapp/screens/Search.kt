package com.shahrukh.movieapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.screens.destinations.MovieDetailsDestination
import com.shahrukh.movieapp.sharedComposables.BackButton
import com.shahrukh.movieapp.sharedComposables.SearchBar
import com.shahrukh.movieapp.sharedComposables.SearchResultItem
import com.shahrukh.movieapp.ui.theme.AppOnPrimaryColor
import com.shahrukh.movieapp.ui.theme.AppPrimaryColor
import com.shahrukh.movieapp.ui.theme.ButtonColor
import com.shahrukh.movieapp.utils.Constants.BASE_POSTER_IMAGE_URL
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.viewmodel.HomeViewModel
import com.shahrukh.movieapp.viewmodel.SearchViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchViewModel: SearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val searchResult = searchViewModel.multiSearchState.value.collectAsLazyPagingItems()
    val includeAdult =
        searchViewModel.includeAdult.value.collectAsState(initial = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .fillMaxWidth(fraction = 0.60F)
        ) {
            val focusManager = LocalFocusManager.current
            BackButton {
                focusManager.clearFocus()
                navigator.navigateUp()
            }

            Text(
                text = "Search",
                modifier = Modifier.padding(start = 50.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = AppOnPrimaryColor
            )
        }

        SearchBar(
            autoFocus = true,
            onSearch = {
                searchViewModel.searchRemoteMovie(includeAdult.value ?: true)
            })


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),


            ){
            items(searchResult.itemCount)
            { index ->
                searchResult[index]?.let {
                    // FeedItem(it)

                    print(it.title)


                    Column(modifier = Modifier
                        .size(160.dp)
                        ) {

                        CoilImage(
                            modifier = Modifier
                                .height(120.dp)
                                .padding(top = 10.dp),
                            imageModel = BASE_POSTER_IMAGE_URL + it.posterPath,
                            shimmerParams = ShimmerParams(
                                baseColor = AppPrimaryColor,
                                highlightColor = ButtonColor,
                                durationMillis = 500,
                                dropOff = 0.65F,
                                tilt = 20F
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        it.title?.let { it1 -> Text(text = it1, color =  Color.White ,modifier = Modifier.padding(start = 10.dp)) }

                    }



                }
            }
        }


    }



    }
