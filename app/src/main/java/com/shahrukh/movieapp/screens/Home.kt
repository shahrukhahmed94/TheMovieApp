package com.shahrukh.movieapp.screens

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.shahrukh.movieapp.model.Film
import com.shahrukh.movieapp.screens.destinations.MovieDetailsDestination
import com.shahrukh.movieapp.screens.destinations.SearchScreenDestination
import com.shahrukh.movieapp.sharedComposables.LoopReverseLottieLoader
import com.shahrukh.movieapp.sharedComposables.SearchBar
import com.shahrukh.movieapp.ui.theme.AppOnPrimaryColor
import com.shahrukh.movieapp.ui.theme.AppPrimaryColor
import com.shahrukh.movieapp.ui.theme.ButtonColor
import com.shahrukh.movieapp.utils.Constants.BASE_BACKDROP_IMAGE_URL
import com.shahrukh.movieapp.utils.Constants.BASE_POSTER_IMAGE_URL
import com.shahrukh.movieapp.utils.FilmType
import com.shahrukh.movieapp.viewmodel.HomeViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import retrofit2.HttpException
import java.io.IOException

@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator?,
    homeViewModel: HomeViewModel = hiltViewModel(),
   // watchListViewModel: WatchListViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))

    ) {


        val popularFilms = homeViewModel.popularFilmsState.value.collectAsLazyPagingItems()









        Row {

            Text(text = "The Movie App", color= Color.White, modifier = Modifier.padding(start = 10.dp, top = 15.dp))
            Spacer(modifier = Modifier.weight(1f))

            IconButton(


                onClick = {
                    navigator?.navigate(
                        direction = SearchScreenDestination()
                    ) {
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp).padding(top = 5.dp, end = 5.dp),
                    painter = painterResource(id = android.R.drawable.ic_menu_search),
                    contentDescription = "search icon",
                    tint = AppOnPrimaryColor
                )
            }
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),


            ) {
            items(popularFilms.itemCount)
            { index ->
                popularFilms[index]?.let {
                    // FeedItem(it)

                    print(it.title)


                    Column(modifier = Modifier
                        .size(160.dp)
                        .clickable {


                            navigator?.navigate(
                                direction = MovieDetailsDestination(
                                    it,
                                    selectedFilmType = FilmType.MOVIE
                                )
                            ) {
                                launchSingleTop = true
                            }


                        }) {

                        com.skydoves.landscapist.coil.CoilImage(
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

                        Text(
                            text = it.title,
                            color = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }


                }
            }
        }


    }


}



